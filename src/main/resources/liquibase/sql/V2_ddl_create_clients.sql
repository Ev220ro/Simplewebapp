--liquibase formatted sql
--changeset rodionov:create_clients logicalFilePath:/

CREATE TABLE clients
(
    id      BIGSERIAL PRIMARY KEY,
    name    varchar(15) NOT NULL,
    surname varchar(25) NOT NULL,
    phone   varchar(15) NOT NULL,
    status  varchar(15) NOT NULL,
    user_id BIGINT      NOT NULL
);

ALTER TABLE clients
    ADD CONSTRAINT FK_user_id FOREIGN KEY (user_id) references users (id);