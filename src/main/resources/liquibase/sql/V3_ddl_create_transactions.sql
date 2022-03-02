--liquibase formatted sql
--changeset rodionov:create_transactions logicalFilePath:/

CREATE TABLE transactions
(
    id        BIGSERIAL PRIMARY KEY,
    date_time timestamp      NOT NULL DEFAULT now(),
    amount    DECIMAL(10, 2) NOT NULL,
    type      varchar(15)    NOT NULL,
    client_id BIGINT         NOT NULL
);
ALTER TABLE transactions
    ADD CONSTRAINT FK_client_id FOREIGN KEY (client_id) REFERENCES clients (id);