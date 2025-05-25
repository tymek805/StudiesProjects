package pl.edu.pwr.ztw.books.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.ApiResponse;
import pl.edu.pwr.ztw.books.models.Book;
import pl.edu.pwr.ztw.books.models.Loan;
import pl.edu.pwr.ztw.books.models.User;
import pl.edu.pwr.ztw.books.repositories.IUserRepository;
import pl.edu.pwr.ztw.books.services.BookService;
import pl.edu.pwr.ztw.books.services.LoanService;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:8080")
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;
    private final IUserRepository userRepository;

    public LoanController(LoanService loanService, BookService bookService, IUserRepository userRepository) {
        this.loanService = loanService;
        this.bookService = bookService;
        this.userRepository = userRepository;
    }

    @GetMapping("/loans")
    public ResponseEntity<ApiResponse<Collection<Loan>>> getLoans() {
        Collection<Loan> loans = loanService.getLoans();
        return ResponseEntity.ok(new ApiResponse<>(loans, "success", "Loans retrieved successfully"));
    }

    @GetMapping("loans/{id}")
    public ResponseEntity<ApiResponse<Loan>> getLoan(@PathVariable Long id) {
        Loan loan = loanService.getLoan(id);
        if (loan != null) {
            return ResponseEntity.ok(new ApiResponse<>(loan, "success", "Loan retrieved successfully"));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(null, "error", "Loan not found"));
        }
    }

    @GetMapping("/users/{userId}/loans")
    public ResponseEntity<ApiResponse<Collection<Loan>>> getUserLoans(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        Collection<Loan> loans = loanService.getUserLoans(user);
        return ResponseEntity.ok(new ApiResponse<>(loans, "success", "User's loans retrieved successfully"));
    }

    @PostMapping("/loans")
    public ResponseEntity<ApiResponse<Loan>> addLoan(@RequestBody LoanRequest loanRequest) {
        Book book = bookService.getBook(loanRequest.bookId);
        User user = userRepository.findById(loanRequest.userId).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "error", "User not found"));
        }
        if (book == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "error", "Book not found"));
        }

        try {
            Loan loan = loanService.addLoan(book, user);
            return ResponseEntity.ok(new ApiResponse<>(loan, "success", "Loan successfully created"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "error", e.getMessage()));
        }
    }

    @PutMapping("/loans/{id}/renew")
    public ResponseEntity<ApiResponse<Loan>> renewLoan(@PathVariable Long id) {
        try {
            Loan loan = loanService.renewLoan(id);
            return ResponseEntity.ok(new ApiResponse<>(loan, "success", "Loan successfully renewed"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "error", e.getMessage()));
        }
    }

    @PutMapping("/loans/{id}/return")
    public ResponseEntity<ApiResponse<Loan>> returnLoan(@PathVariable Long id) {
        try {
            Loan loan = loanService.returnLoan(id);
            return ResponseEntity.ok(new ApiResponse<>(loan, "success", "Loan successfully returned"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "error", e.getMessage()));
        }
    }

    @DeleteMapping("loans/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLoan(@PathVariable Long id) {
        boolean deleted = loanService.deleteLoan(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(null, "success", "Loan successfully deleted"));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(null, "error", "Loan not found"));
        }
    }

    public static class LoanRequest {
        private Long bookId;
        private Long userId;

        public Long getBookId() {
            return bookId;
        }

        public void setBookId(Long bookId) {
            this.bookId = bookId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }
}
