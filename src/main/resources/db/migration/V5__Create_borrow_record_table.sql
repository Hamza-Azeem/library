CREATE TABLE borrow_record(
    patron_id BIGINT,
    book_id BIGINT,
    borrow_date DATE,
    return_date DATE,
    PRIMARY KEY (patron_id, book_id),
    FOREIGN KEY(patron_id) REFERENCES patron(id),
    FOREIGN KEY(book_id) REFERENCES book(id)
);