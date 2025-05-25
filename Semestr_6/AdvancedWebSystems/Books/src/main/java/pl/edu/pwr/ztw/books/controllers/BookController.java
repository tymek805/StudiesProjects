package pl.edu.pwr.ztw.books.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.dto.BookDTO;
import pl.edu.pwr.ztw.books.models.Book;
import pl.edu.pwr.ztw.books.services.IBookService;
import pl.edu.pwr.ztw.books.ApiResponse;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:8080")
public class BookController {
    private final IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/books")
    public ResponseEntity<ApiResponse<?>> getBooks(@RequestParam(name = "page", defaultValue = "0") int page,
                                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(new ApiResponse<>(bookService.getBooks(pageable), "success", "Books retrieved successfully"));
    }

    @GetMapping(value = "/book/{id}")
    public ResponseEntity<ApiResponse<?>> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        if (book != null) {
            return ResponseEntity.ok(new ApiResponse<>(book, "success", "Book retrieved successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse<>(null, "error", "Book not found"));
    }

    @PostMapping("/book")
    public ResponseEntity<ApiResponse<?>> addBook(@RequestBody BookDTO bookDTO) {
        Book book = bookService.addBook(bookDTO);
        return ResponseEntity.ok(new ApiResponse<>(book, "success", "Book added successfully"));
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<ApiResponse<?>> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        Book result = bookService.updateBook(id, bookDTO);

        if (result == null) {
            return ResponseEntity.status(400).body(new ApiResponse<>(null, "error", "Invalid data or book not found"));
        }

        return ResponseEntity.ok(new ApiResponse<>(result, "success", "Book updated successfully"));
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<ApiResponse<?>> deleteBook(@PathVariable Long id) {
        boolean result = bookService.deleteBook(id);
        if (!result) {
            return ResponseEntity.status(404).body(new ApiResponse<>(null, "error", "Book not found"));
        }
        return ResponseEntity.ok(new ApiResponse<>(null, "success", "Book deleted successfully"));
    }
}
