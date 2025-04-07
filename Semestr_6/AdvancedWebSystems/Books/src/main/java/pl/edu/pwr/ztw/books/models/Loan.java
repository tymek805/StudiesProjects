package pl.edu.pwr.ztw.books.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    @NotNull
    private LocalDate loanDate;

    @NonNull
    private LocalDate plannedReturnDate;

    private LocalDate returnDate;

    private float fine;

    public Loan() {}

    public Loan(User user, Book book, LocalDate loanDate, LocalDate plannedReturnDate) {
        this.user = user;
        this.book = book;
        this.loanDate = loanDate;
        this.plannedReturnDate = plannedReturnDate;
        this.returnDate = null;
        this.fine = 0.0f;
    }

    public float calculateFine()
    {
        if ((returnDate != null && returnDate.isAfter(plannedReturnDate))) {
            long daysLate = ChronoUnit.DAYS.between(plannedReturnDate, returnDate);
            this.fine = daysLate * 0.2f;
        } else if (plannedReturnDate.isBefore(LocalDate.now())) {
            long daysLate = ChronoUnit.DAYS.between(plannedReturnDate, LocalDate.now());
            this.fine = daysLate * 0.2f;
        }
        else {
            this.fine = 0.0f;
        }
        return fine;
    }

    // Getter i Setter dla 'id'
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter i Setter dla 'user'
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getter i Setter dla 'book'
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    // Getter i Setter dla 'loanDate'
    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    // Getter i Setter dla 'plannedReturnDate'
    public LocalDate getPlannedReturnDate() {
        return plannedReturnDate;
    }

    public void setPlannedReturnDate(LocalDate plannedReturnDate) {
        this.plannedReturnDate = plannedReturnDate;
    }

    // Getter i Setter dla 'returnDate'
    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        calculateFine();
    }

    // Getter i Setter dla 'fine'
    public float getFine() {
        return fine;
    }

    public void setFine(float fine) {
        this.fine = fine;
    }
}


