--liquibase formatted sql
--changeset user:create_database logicalFilePath:/
CREATE TABLE USERS(
     id BIGSERIAL PRIMARY KEY,
     login varchar(50) NOT NULL,
     password varchar(255) NOT NULL,
     name varchar(15) NOT NULL,
     role varchar(15) NOT NULL,
     creator_id BIGINT
);
ALTER TABLE USERS ADD CONSTRAINT FK_Creator_id
FOREIGN KEY (creator_id) references USERS (id)