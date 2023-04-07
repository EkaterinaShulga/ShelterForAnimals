-- liquibase formatted sql

-- changeset LeonidB:1
CREATE TABLE Cats
(
    id        BIGSERIAL PRIMARY KEY,
    name      TEXT,
    age       TEXT,
    color     TEXT,
    chat_id   BIGSERIAL,
    cat_Photo TEXT
);

CREATE TABLE Clients
(
    id      BIGSERIAL PRIMARY KEY,
    chat_id BIGSERIAL,
    status  INT
);


CREATE TABLE Contacts
(
    id            serial PRIMARY KEY,
    chat_id       BIGSERIAL,
    name          TEXT,
    number_phone  TEXT,
    date          DATE,
    client_status int

);

CREATE TABLE Dogs
(
    id        BIGSERIAL PRIMARY KEY,
    name      TEXT,
    age       TEXT,
    color     TEXT,
    chat_id   BIGSERIAL,
    dog_Photo TEXT

);

CREATE TABLE Records
(
    record_id          BIGSERIAL PRIMARY KEY,
    status             int,
    chat_id            BIGSERIAL,
    date               DATE,
    diet               TEXT,
    adaptation         TEXT,
    change_in_behavior TEXT

);

CREATE TABLE Pet_Photos
(
    pet_Photo_id BIGSERIAL PRIMARY KEY,
    file_path    TEXT,
    file_size    BIGINT,
    date         DATE,
    status       int,
    chat_id      BIGSERIAL,
    record_id    bigint REFERENCES Records (record_id) ON DELETE CASCADE

);



CREATE TABLE Users
(
    user_id      BIGSERIAL PRIMARY KEY,
    user_name    TEXT,
    number_phone TEXT,
    chat_Id      BIGSERIAL UNIQUE,
    status       int,
    pet_name     TEXT
);

CREATE TABLE Volunteers
(
    id      BIGSERIAL PRIMARY KEY,
    name    TEXT,
    status  INT,
    chat_Id BIGSERIAL
);





