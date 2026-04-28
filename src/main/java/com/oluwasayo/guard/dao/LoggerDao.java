package com.oluwasayo.guard.dao;

import com.oluwasayo.guard.dto.IngestionRequestDto;
import com.oluwasayo.guard.model.TransactionLog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<TransactionLog> fetchFlaggedAttempts(int page, int size) {
        int offset = page * size;
        String sql = """
                SELECT * 
                FROM transaction_logs
                WHERE status IN ('BLACKLISTED', 'RATE_LIMITED')
                ORDER BY created_at DESC
                OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;
                """;

        return jdbcTemplate.query(
                sql,
                new Object[]{offset, size},
                (rs, rowNum) -> {
                    TransactionLog transactionLog = new TransactionLog();
                    transactionLog.setId(rs.getInt("id"));
                    transactionLog.setCardNo(rs.getString("card_no"));
                    transactionLog.setAmount(rs.getLong("amount"));
                    transactionLog.setMerchantId(rs.getString("merchant_id"));
                    transactionLog.setIpAddress(rs.getString("ip_address"));
                    transactionLog.setStatus(rs.getString("status"));
                    transactionLog.setExecutionTime(rs.getLong("execution_time"));
                    transactionLog.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    return transactionLog;
                }
        );
    }
}