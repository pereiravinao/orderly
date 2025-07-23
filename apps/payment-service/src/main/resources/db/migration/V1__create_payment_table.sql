CREATE TABLE IF NOT EXISTS tb_payment
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id       VARCHAR(255)   NOT NULL,
    transaction_id VARCHAR(255)   NOT NULL UNIQUE,
    amount         DECIMAL(10, 2) NOT NULL,
    card_number    VARCHAR(16)    NOT NULL,
    status         VARCHAR(50)    NOT NULL,
    created_at     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);
