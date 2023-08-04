-- liquibase formatted sql

-- changeset northin:1

CREATE TABLE socks
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    color      VARCHAR(255) not null,
    cotton_part SMALLINT CHECK (cotton_part >= 0 AND cotton_part <= 100),
    quantity   INT CHECK ( quantity > 0 )
);