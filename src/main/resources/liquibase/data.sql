-- liquibase formatted sql

-- changeset LeonidB:1
CREATE TABLE cats
(
    id        BIGSERIAL PRIMARY KEY,
    name      TEXT,
    age       TEXT,
    color       TEXT,
    chat_id   BIGSERIAL,
    cat_Photo TEXT
);

CREATE TABLE clients
(
    id      BIGSERIAL PRIMARY KEY,
    chat_id BIGSERIAL,
    status  INT
);


CREATE TABLE contacts
(
    id            serial PRIMARY KEY,
    name          TEXT,
    number_phone  TEXT,
    date_time     TIMESTAMP WITHOUT TIME ZONE,
    client_status int,
    id_client     bigint references clients (id) on delete cascade

);

CREATE TABLE dogs
(
    id        BIGSERIAL PRIMARY KEY,
    name      TEXT,
    age       TEXT,
    color       TEXT,
    chat_id   BIGSERIAL,
    dog_Photo TEXT

);


CREATE TABLE pet_Photos
(
    pet_Photo_id BIGSERIAL  PRIMARY KEY,
    file_path     TEXT,
    file_size     BIGINT,
    status        int,
    chat_id       BIGSERIAL

);

CREATE TABLE records
(
    record_id          BIGSERIAL PRIMARY KEY,
    status             int,
    chat_id            BIGSERIAL,
    date_time          TIMESTAMP WITHOUT TIME ZONE,
    diet               TEXT,
    adaptation         TEXT,
    change_in_behavior TEXT,
        pet_Photo_id bigint REFERENCES pet_Photos (pet_Photo_id) ON DELETE CASCADE

);
CREATE TABLE reports
(
    report_id BIGSERIAL PRIMARY KEY,
    chat_id   BIGSERIAL,
    status             int,
    photo_id  bigint REFERENCES pet_Photos (pet_Photo_id) ON DELETE CASCADE,
    record_id bigint REFERENCES records (record_id) ON DELETE CASCADE

);

CREATE TABLE users
(
    user_id      BIGSERIAL PRIMARY KEY,
    user_name    TEXT,
    number_phone TEXT,
    chat_Id      BIGSERIAL UNIQUE,
    status       int,
    pet_name     TEXT
);

CREATE TABLE volunteers
(
    id      BIGSERIAL PRIMARY KEY,
    name    TEXT,
    status  INT,
    chat_Id BIGSERIAL
);


CREATE SEQUENCE IF NOT EXISTS hibernate_sequence;

