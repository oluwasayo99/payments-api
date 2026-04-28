package com.oluwasayo.guard.controller;

import com.oluwasayo.guard.dao.LoggerDao;
import com.oluwasayo.guard.dto.IngestionRequestDto;
import com.oluwasayo.guard.dto.IngestionResponseDto;
import com.oluwasayo.guard.model.TransactionLog;
import com.oluwasayo.guard.service.BlacklistService;
import com.oluwasayo.guard.service.RateLimiter;
import com.oluwasayo.transactions.mapper.TransactionMapper;
import com.oluwasayo.transactions.model.TransactionModel;
import com.oluwasayo.transactions.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/guard")
public class IngestionController {
    private final RateLimiter rateLimiter;
    private final BlacklistService blacklistService;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final LoggerDao logger;

    public IngestionController(RateLimiter rateLimiter, BlacklistService blacklistService, TransactionService transactionService, TransactionMapper transactionMapper,  LoggerDao logger) {
        this.rateLimiter = rateLimiter;
        this.blacklistService = blacklistService;
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
        this.logger = logger;
    }

    @PostMapping("/ingest")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<IngestionResponseDto<Void>> ingest(@RequestBody IngestionRequestDto request) {
        long start = System.currentTimeMillis();

        String status = "PENDING";

        if (!rateLimiter.isAllowed(request.getMerchantId())) {
            status = "RATE_LIMITED";
            logger.logAttempt(request, status, System.currentTimeMillis() - start);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(new IngestionResponseDto<>(status, "Rate limit exceeded", null, System.currentTimeMillis() - start));
        }

        if (blacklistService.isBlacklisted(request.getMerchantId())) {
            status = "BLACKLISTED";
            logger.logAttempt(request, status, System.currentTimeMillis() - start);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new IngestionResponseDto<>(status, "Merchant is blacklisted", null, System.currentTimeMillis() - start));
        }

        TransactionModel tx = transactionMapper.ingestionToTransaction(request, status);
        transactionService.save(tx);
        logger.logAttempt(request, status, System.currentTimeMillis() - start);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new IngestionResponseDto<>(status, "Transaction processed", null, System.currentTimeMillis() - start));
    }

    @GetMapping("/flagged-attempts")
    public ResponseEntity<List<TransactionLog>> getFlaggedAttempts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<TransactionLog> results = logger.fetchFlaggedAttempts(page, size);

        return ResponseEntity.ok(results);
    }



}
