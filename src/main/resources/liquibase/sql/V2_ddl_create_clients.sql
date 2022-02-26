--liquibase formatted sql
--changeset rodionov:create_clients logicalFilePath:/

CREATE TABLE clients
(
    client_id BIGSERIAL PRIMARY KEY,
    name      varchar(15) NOT NULL,
    surname   varchar(25) NOT NULL,
    phone     varchar(15) NOT NULL,
    status    varchar(15) NOT NULL,
    user_id   BIGINT      NOT NULL
);


-- ALTER TABLE USERS ADD CONSTRAINT FK_Creator_id
--     FOREIGN KEY (creator_id) references USERS (id)