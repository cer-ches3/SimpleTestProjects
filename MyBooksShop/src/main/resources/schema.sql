DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;

CREATE TABLE  books(
                       id BIGSERIAL PRIMARY KEY,
                       author_id BIGINT NOT NULL,
                       title VARCHAR(250) NOT NULL,
                       price_old INTEGER DEFAULT NULL,
                       price INTEGER DEFAULT NULL
);

DROP TABLE IF EXISTS authors;

create table authors (
                         id BIGSERIAL PRIMARY KEY,
                         first_name VARCHAR(50),
                         last_name VARCHAR(50)
);