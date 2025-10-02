-- Автор: Шилов Кирилл
-- Миграция для создания таблиц: Card, CardBlockRequest, Transfer, User

CREATE TABLE reg_user (
                          id BIGSERIAL PRIMARY KEY,
                          login VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL
);

CREATE TABLE card (
                      id BIGSERIAL PRIMARY KEY,
                      number VARCHAR(255) NOT NULL,
                      owner VARCHAR(255) NOT NULL,
                      expiry_date DATE NOT NULL,
                      balance DECIMAL(11, 2) NOT NULL DEFAULT 0,
                      card_status VARCHAR(255) NOT NULL,
                      user_id BIGINT,
                      CONSTRAINT fk_card_user FOREIGN KEY (user_id) REFERENCES reg_user(id) ON DELETE SET NULL
);

CREATE TABLE card_block_request (
                      id BIGSERIAL PRIMARY KEY,
                      card_id BIGINT NOT NULL,
                      message VARCHAR(255) NOT NULL,
                      CONSTRAINT fk_card_block_request_card FOREIGN KEY (card_id) REFERENCES card(id) ON DELETE CASCADE
);

CREATE TABLE transfer (
                      id BIGSERIAL PRIMARY KEY,
                      card_number_from BIGINT NOT NULL,
                      card_number_to BIGINT NOT NULL,
                      transfer_date TIMESTAMP NOT NULL,
                      status VARCHAR(255) NOT NULL,
                      amount DECIMAL(11, 2) NOT NULL CHECK (amount > 0)
);
CREATE TABLE token_store_entity (
                          id BIGSERIAL PRIMARY KEY,
                          login VARCHAR(255) NOT NULL,
                          token VARCHAR(255) NOT NULL
);
