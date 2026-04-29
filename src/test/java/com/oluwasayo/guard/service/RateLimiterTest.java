package com.oluwasayo.guard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RateLimiterTest {

    private RateLimiter rateLimiter;

    @BeforeEach
    void setUp() {
        rateLimiter = new RateLimiter();
    }

    @Test
    void isAllowed_UnderLimit_ReturnsTrue() {
        String ip = "192.168.1.1";
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.isAllowed(ip), "Request " + (i + 1) + " should be allowed");
        }
    }

    @Test
    void isAllowed_OverLimit_ReturnsFalse() {
        String ip = "192.168.1.2";
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.isAllowed(ip));
        }
        assertFalse(rateLimiter.isAllowed(ip), "6th request should be blocked");
    }

    @Test
    void isAllowed_DifferentIps_TrackedSeparately() {
        String ip1 = "192.168.1.3";
        String ip2 = "192.168.1.4";

        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.isAllowed(ip1));
        }
        assertFalse(rateLimiter.isAllowed(ip1));
        
        assertTrue(rateLimiter.isAllowed(ip2), "Request from different IP should be allowed");
    }
}
