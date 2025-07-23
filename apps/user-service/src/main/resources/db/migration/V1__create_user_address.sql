CREATE TABLE IF NOT EXISTS tb_user
(
    id         INTEGER PRIMARY KEY AUTO_INCREMENT,
    auth_id    INTEGER      NOT NULL UNIQUE,
    email      VARCHAR(255) NOT NULL UNIQUE,
    cpf        VARCHAR(11)  NOT NULL UNIQUE,
    name       VARCHAR(255) NOT NULL,
    phone      VARCHAR(15)  NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tb_address
(
    id         INTEGER PRIMARY KEY AUTO_INCREMENT,
    user_id    INTEGER      NOT NULL REFERENCES tb_user (id) ON DELETE CASCADE,
    street     VARCHAR(255) NOT NULL,
    number     VARCHAR(10)  NOT NULL,
    complement VARCHAR(255),
    city       VARCHAR(255) NOT NULL,
    state      VARCHAR(2)   NOT NULL,
    zip_code   VARCHAR(8)   NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);


