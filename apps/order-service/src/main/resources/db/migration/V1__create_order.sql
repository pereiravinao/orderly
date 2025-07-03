CREATE TABLE IF NOT EXISTS tb_order
(
    id         INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id    INTEGER        NOT NULL,
    payment_id INTEGER        NOT NULL,
    products   JSONB          NOT NULL,
    status     VARCHAR(20)    NOT NULL DEFAULT 'ABERTO',
    total      DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);