package pl.edu.pwr.ztw.books.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.dto.BookDTO;
import pl.edu.pwr.ztw.books.models.Book;
import pl.edu.pwr.ztw.books.services.IBookService;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    private final IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/books")
    public ResponseEntity<Object> getBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping(value = "/book/{id}")
    public ResponseEntity<Object> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @PostMapping("/book")
    public ResponseEntity<Object> addBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.addBook(bookDTO));
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        Book result = bookService.updateBook(id, bookDTO);

        if (result == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id) {
        boolean result = bookService.deleteBook(id);
        if (!result)
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }
}
