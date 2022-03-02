--liquibase formatted sql
--changeset rodionov:create_actions logicalFilePath:/

CREATE TABLE actions
(
    id        BIGSERIAL PRIMARY KEY,
    data_time timestamp    NOT NULL DEFAULT now(),
    type      varchar(50)  NOT NULL,
    details   varchar(255) NOT NULL,
    user_id   BIGINT       NOT NULL
);
ALTER TABLE actions
    ADD CONSTRAINT FK_user_id FOREIGN KEY (user_id) references users (id);