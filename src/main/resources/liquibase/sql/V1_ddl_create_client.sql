--liquibase formatted sql
--changeset user:create_database logicalFilePath:/
CREATE TABLE CLIENTS(
                      client_id BIGSERIAL PRIMARY KEY,
                      name varchar(15) NOT NULL,
                      surname varchar(25) NOT NULL,
                      phone varchar(15) NOT NULL,
                      status varchar(15) NOT NULL,
                      user BIGINT NOT NULL
);


-- ALTER TABLE USERS ADD CONSTRAINT FK_Creator_id
--     FOREIGN KEY (creator_id) references USERS (id)