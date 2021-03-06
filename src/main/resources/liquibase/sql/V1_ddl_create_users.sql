--liquibase formatted sql
--changeset rodionov:create_users logicalFilePath:/

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    login      varchar(50)  NOT NULL,
    password   varchar(255) NOT NULL,
    name       varchar(15)  NOT NULL,
    role       varchar(15)  NOT NULL,
    creator_id BIGINT
);
ALTER TABLE users
    ADD CONSTRAINT FK_creator_id FOREIGN KEY (creator_id) references users (id);