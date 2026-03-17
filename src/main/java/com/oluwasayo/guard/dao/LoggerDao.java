package com.oluwasayo.guard.dao;

import com.oluwasayo.guard.dto.IngestionRequestDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

@Repository
public class LoggerDao {

    private final JdbcTemplate jdbcTemplate;

    public LoggerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Async
    public void logAttempt(IngestionRequestDto dto, String status, long executionTime) {
        String sql = """
                INSERT INTO transaction_logs
                (card_no, amount, merchant_id, ip_address, status, execution_time)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                dto.getCardNo(),
                dto.getAmount(),
                dto.getMerchantId(),
                dto.getIpAddress(),
                status,
                executionTime
        );
    }
}