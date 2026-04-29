package com.oluwasayo.guard.controller;

import com.oluwasayo.guard.dao.LoggerDao;
import com.oluwasayo.guard.dto.IngestionRequestDto;
import com.oluwasayo.guard.dto.IngestionResponseDto;
import com.oluwasayo.guard.service.BlacklistService;
import com.oluwasayo.guard.service.RateLimiter;
import com.oluwasayo.transactions.mapper.TransactionMapper;
import com.oluwasayo.transactions.model.TransactionModel;
import com.oluwasayo.transactions.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngestionControllerTest {

    @Mock private RateLimiter rateLimiter;
    @Mock private BlacklistService blacklistService;
    @Mock private TransactionService transactionService;
    @Mock private TransactionMapper transactionMapper;
    @Mock private LoggerDao loggerDao;

    @InjectMocks
    private IngestionController ingestionController;

    private IngestionRequestDto request;

    @BeforeEach
    void setUp() {
        request = new IngestionRequestDto();
        request.setIpAddress("127.0.0.1");
        request.setMerchantId("merchant-123");
        request.setAmount(100L);
    }

    @Test
    void ingest_RateLimitExceeded_Returns429() {
        when(rateLimiter.isAllowed("127.0.0.1")).thenReturn(false);

        ResponseEntity<IngestionResponseDto<Void>> response = ingestionController.ingest(request);

        assertEquals(HttpStatus.TOO_MANY_REQUESTS, response.getStatusCode());
        assertEquals("RATE_LIMITED", response.getBody().getStatus());
        verify(loggerDao).logAttempt(eq(request), eq("RATE_LIMITED"), anyLong());
    }

    @Test
    void ingest_BlacklistedMerchant_Returns403() {
        when(rateLimiter.isAllowed("127.0.0.1")).thenReturn(true);
        when(blacklistService.isBlacklisted("merchant-123")).thenReturn(true);

        ResponseEntity<IngestionResponseDto<Void>> response = ingestionController.ingest(request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("BLACKLISTED", response.getBody().getStatus());
        verify(loggerDao).logAttempt(eq(request), eq("BLACKLISTED"), anyLong());
    }

    @Test
    void ingest_Success_Returns201() {
        when(rateLimiter.isAllowed("127.0.0.1")).thenReturn(true);
        when(blacklistService.isBlacklisted("merchant-123")).thenReturn(false);
        when(transactionMapper.ingestionToTransaction(eq(request), eq("PENDING"))).thenReturn(new TransactionModel());

        ResponseEntity<IngestionResponseDto<Void>> response = ingestionController.ingest(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("PENDING", response.getBody().getStatus());
        verify(transactionService).save(any(TransactionModel.class));
        verify(loggerDao).logAttempt(eq(request), eq("PENDING"), anyLong());
    }
}
