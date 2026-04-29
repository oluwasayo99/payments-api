package com.oluwasayo.guard.service;

import com.oluwasayo.guard.repository.BlacklistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlacklistServiceTest {

    @Mock
    private BlacklistRepository blacklistRepository;

    private BlacklistService blacklistService;

    @BeforeEach
    void setUp() {
        blacklistService = new BlacklistService(blacklistRepository);
    }

    @Test
    void isBlacklisted_WhenBlacklisted_ReturnsTrue() {
        String merchantId = "bad-merchant";
        when(blacklistRepository.existsByMerchantId(merchantId)).thenReturn(true);
        assertTrue(blacklistService.isBlacklisted(merchantId));
    }

    @Test
    void isBlacklisted_WhenNotBlacklisted_ReturnsFalse() {
        String merchantId = "good-merchant";
        when(blacklistRepository.existsByMerchantId(merchantId)).thenReturn(false);
        assertFalse(blacklistService.isBlacklisted(merchantId));
    }
}
