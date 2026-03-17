package com.oluwasayo.guard.controller;

import com.oluwasayo.guard.dao.LoggerDao;
import com.oluwasayo.guard.dto.IngestionRequestDto;
import com.oluwasayo.guard.service.BlacklistService;
import com.oluwasayo.guard.service.RateLimiter;
import com.oluwasayo.transactions.mapper.TransactionMapper;
import com.oluwasayo.transactions.model.TransactionModel;
import com.oluwasayo.transactions.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    public ResponseEntity<String> ingest(@RequestBody IngestionRequestDto request) {
        System.out.println("The full data received is: " + request.toString());
        long start = System.currentTimeMillis();

        String status = "PENDING";

        if(!rateLimiter.isAllowed(request.getMerchantId())) {
            status = "RATE_LIMITED";
            logger.logAttempt(request, status, System.currentTimeMillis() - start);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(status);
        }

        if(blacklistService.isBlacklisted(request.getMerchantId())) {
            status = "BLACKLISTED";
            logger.logAttempt(request, status, System.currentTimeMillis() - start);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(status);
        }

        TransactionModel tx =  transactionMapper.ingestionToTransaction(request, status);
        transactionService.save(tx);
        logger.logAttempt(request, status, System.currentTimeMillis() - start);

        return ResponseEntity.status(HttpStatus.CREATED).body(status);
    }

    public ResponseEntity<?> getFlaggedAttempts() {
        return ResponseEntity.ok().build();
    }



}
