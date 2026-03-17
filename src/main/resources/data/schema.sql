CREATE TABLE public.transactions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  -- @Id with GenerationType.IDENTITY
    status VARCHAR(50) NOT NULL,           -- Transaction status
    amount BIGINT NOT NULL,                 -- Amount in smallest currency unit (e.g., cents)
    currency VARCHAR(10) NOT NULL,          -- Currency code (ISO 4217)
    transaction_date TIMESTAMP NOT NULL,    -- LocalDateTime
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Creation timestamp
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP, -- Update timestamp
    sender_id VARCHAR(100) NOT NULL,        -- Sender identifier
    receiver_id VARCHAR(100) NOT NULL       -- Receiver identifier
);