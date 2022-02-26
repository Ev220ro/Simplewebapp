--liquibase formatted sql
--changeset user:create_database logicalFilePath:/
CREATE TABLE TRANSACTIONS(
                        transaction_id BIGSERIAL PRIMARY KEY,
                        dateTimeTransaction varchar(25) NOT NULL ,
                        sum real NOT NULL,
                        transactionType varchar(15) NOT NULL,
                        client_id BIGINT NOT NULL
);
