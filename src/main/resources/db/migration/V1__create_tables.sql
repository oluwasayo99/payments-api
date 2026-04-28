-- blacklisted_merchants
CREATE TABLE blacklisted_merchants
(
    id          bigint IDENTITY (1, 1) NOT NULL PRIMARY KEY,
    merchant_id varchar(255),
    reason      varchar(255),
    created_at  datetime NOT NULL DEFAULT GETDATE()
);

-- transaction_logs
CREATE TABLE transaction_logs
(
    id             bigint IDENTITY (1, 1) NOT NULL PRIMARY KEY,
    card_no        varchar(255),
    amount         bigint,
    merchant_id    varchar(255),
    ip_address     varchar(255),
    status         varchar(255),
    execution_time bigint,
    created_at     datetime NOT NULL DEFAULT GETDATE()
);

-- transactions
CREATE TABLE transactions
(
    id               bigint IDENTITY (1, 1) NOT NULL PRIMARY KEY,
    status           varchar(255),
    amount           bigint,
    currency         varchar(255),
    transaction_date datetime,
    created_at       datetime NOT NULL DEFAULT GETDATE(),
    updated_at       datetime NOT NULL DEFAULT GETDATE(),
    sender_id        varchar(255),
    receiver_id      varchar(255)
);