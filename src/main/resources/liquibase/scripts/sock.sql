-- liquibase formatted sql

-- changeset northin:1

CREATE TABLE socks (
                       id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                       color VARCHAR(255),
                       cottonPart SMALLINT CHECK (cottonPart >= 0 AND cottonPart <= 100),
                       quantity INT
);