package com.hamza.librarymanagementsystem.book;

import com.hamza.librarymanagementsystem.exception.DuplicateRecordException;
import com.hamza.librarymanagementsystem.exception.RecordNotFoundException;
import com.hamza.librarymanagementsystem.exception.RecordNotModifiedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookService {
    private final BookDao bookDao;

    BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }
    public List<Book> findAllBooks(){
        return bookDao.selectAllBooks();
    }

    public Book findBookById(long id){
        return bookDao.selectBookById(id).orElseThrow(
                ()-> new RecordNotFoundException(String.format("No book with id=%s was found.", id)));
    }
    public void addNewBook(BookRegistrationRequest bookRegistrationRequest){
        // verify that there is no book with the same isbn exist.
        if(bookDao.bookExistsWithIsbn(bookRegistrationRequest.ISBN())){
            throw new DuplicateRecordException("This ISBN is already taken.");
        }
        Book newBook = new Book(
                bookRegistrationRequest.title(),
                bookRegistrationRequest.author(),
                bookRegistrationRequest.publicationYear(),
                bookRegistrationRequest.ISBN()
        );
        bookDao.addBook(newBook);
    }
    public void updateExistingBook(long id, BookUpdateRequest bookUpdateRequest){
        Book book = bookDao.selectBookById(id).orElseThrow(
                ()-> new RecordNotFoundException(String.format("No book with id=%s was found.", id)));
        boolean changed = false;
        if(bookUpdateRequest.title() != null && !bookUpdateRequest.title().equals(book.getTitle())){
            book.setTitle(bookUpdateRequest.title());
            changed=true;
        }
        if(bookUpdateRequest.author() != null && !bookUpdateRequest.author().equals(book.getAuthor())){
            book.setAuthor(bookUpdateRequest.author());
            changed=true;
        }
        if((Integer)bookUpdateRequest.publicationYear() != null && bookUpdateRequest.publicationYear()!=book.getPublicationYear()){
            book.setPublicationYear(bookUpdateRequest.publicationYear());
            changed=true;
        }
        if(bookUpdateRequest.ISBN() != null && !bookUpdateRequest.ISBN().equals(book.getISBN())){
            if(bookDao.bookExistsWithIsbn(bookUpdateRequest.ISBN())){
                throw new DuplicateRecordException("This ISBN is already taken.");
            }
            book.setISBN(bookUpdateRequest.ISBN());
            changed=true;
        }
        if(!changed){
            throw new RecordNotModifiedException("No updates found!");
        }
        bookDao.updateBook(book);
    }
    public void deleteBookById(long id){
        Book book = bookDao.selectBookById(id).orElseThrow(
                ()-> new RecordNotFoundException(String.format("No book with id=%s was found.", id)));
        bookDao.deleteBookById(id);
    }

}
