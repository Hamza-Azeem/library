CREATE TABLE patron(
    id BIGSERIAL PRIMARY KEY,
    name varchar(60),
    email varchar(150),
    phone_number varchar(30),
    address TEXT
);
CREATE TABLE book(
    id BIGSERIAL PRIMARY KEY,
    title varchar(100),
    author varchar(60),
    publication_year INTEGER,
    ISBN char(13)
);
