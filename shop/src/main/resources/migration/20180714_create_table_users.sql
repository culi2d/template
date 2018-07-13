CREATE TABLE users (
    id bigserial PRIMARY KEY,
    email varchar(255) UNIQUE,
    password varchar(255),
    name varchar(255),
    type int
);
