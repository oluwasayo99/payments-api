-- V1__Init_Guard_And_Transactions.sql

-- =========================
-- Blacklisted merchants
-- =========================
CREATE TABLE blacklisted_merchants (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       merchant_id VARCHAR(50) UNIQUE NOT NULL,
       reason VARCHAR(255),
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- Transaction logs
-- =========================
CREATE TABLE transaction_logs (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      card_no VARCHAR(20),
      amount BIGINT NOT NULL,
      merchant_id VARCHAR(50),
      ip_address VARCHAR(45),
      status VARCHAR(20),
      execution_time BIGINT,  -- matches your DAO
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- Transactions
-- =========================
CREATE TABLE transactions (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,           -- @Id with GenerationType.IDENTITY
      status VARCHAR(50) NOT NULL,                   -- Transaction status
      amount BIGINT NOT NULL,                        -- Amount in smallest currency unit (e.g., cents)
      currency VARCHAR(10) NOT NULL,                 -- Currency code (ISO 4217)
      transaction_date TIMESTAMP NOT NULL,           -- LocalDateTime
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Creation timestamp
      updated_at TIMESTAMP,                           -- Update timestamp (handle via Java/Hibernate)
      sender_id VARCHAR(100) NOT NULL,               -- Sender identifier
      receiver_id VARCHAR(100) NOT NULL              -- Receiver identifier
);