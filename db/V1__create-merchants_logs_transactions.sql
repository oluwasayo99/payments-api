CREATE TABLE blacklisted_merchants
(
    id          bigint IDENTITY (1, 1) NOT NULL,
    merchant_id varchar(255),
    reason      varchar(255),
    created_at  datetime               NOT NULL,
    CONSTRAINT pk_blacklisted_merchants PRIMARY KEY (id)
);

CREATE TABLE transaction_logs
(
    id             bigint IDENTITY (1, 1) NOT NULL,
    card_no        varchar(255),
    amount         bigint,
    merchant_id    varchar(255),
    ip_address     varchar(255),
    status         varchar(255),
    execution_time bigint,
    created_at     datetime,
    CONSTRAINT pk_transaction_logs PRIMARY KEY (id)
);

CREATE TABLE transactions
(
    id               bigint IDENTITY (1, 1) NOT NULL,
    status           varchar(255),
    amount           bigint,
    currency         varchar(255),
    transaction_date datetime,
    created_at       datetime,
    updated_at       datetime,
    sender_id        varchar(255),
    receiver_id      varchar(255),
    CONSTRAINT pk_transactions PRIMARY KEY (id)
);