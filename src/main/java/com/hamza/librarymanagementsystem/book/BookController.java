package com.hamza.librarymanagementsystem.book;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping
    private ResponseEntity<?> findAllBooks(){
        return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
    }
    @GetMapping("/{bookId}")
    private ResponseEntity<?> findBookById(@PathVariable long bookId){
        return new ResponseEntity<>(bookService.findBookById(bookId), HttpStatus.OK);
    }
    @PostMapping
    private ResponseEntity<?> saveBook(@RequestBody @Valid BookRegistrationRequest bookRegistrationRequest){
        bookService.addNewBook(bookRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("{}");
    }
    @PutMapping("/{bookId}")
    private ResponseEntity<?> updateBook(@PathVariable long bookId, @RequestBody BookUpdateRequest bookUpdateRequest){
        bookService.updateExistingBook(bookId, bookUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body("{}");
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteBook(@PathVariable long id){
        bookService.deleteBookById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{}");
    }

}
