package pl.edu.pwr.ztw.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.edu.pwr.ztw.books.models.Author;
import pl.edu.pwr.ztw.books.models.Book;
import pl.edu.pwr.ztw.books.models.Loan;
import pl.edu.pwr.ztw.books.models.User;
import pl.edu.pwr.ztw.books.repositories.IAuthorRepository;
import pl.edu.pwr.ztw.books.repositories.IBookRepository;
import pl.edu.pwr.ztw.books.repositories.ILoanRepository;
import pl.edu.pwr.ztw.books.repositories.IUserRepository;
import pl.edu.pwr.ztw.books.services.IBookService;
import pl.edu.pwr.ztw.books.services.LoanService;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final IBookRepository bookRepository;
    private final IAuthorRepository authorRepository;
    private final IUserRepository userRepository;
    private final ILoanRepository loanRepository;


    public DataLoader(IBookRepository bookRepository, IAuthorRepository authorRepository, IUserRepository userRepository, ILoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public void run(String... args) {
        Author author1 = new Author("Henryk", "Sienkiewicz");
        Author author2 = new Author("Stanisław", "Reymont");
        Author author3 = new Author("Adam", "Mickiewicz");
        authorRepository.saveAll(List.of(
                author1, author2, author3
        ));

        Book book1 = new Book("Potop", author1, 936);
        Book book2 = new Book("Wesele", author2, 150);
        Book book3 = new Book("Dziady", author3, 292);

        bookRepository.saveAll(List.of(
                book1, book2, book3
        ));

        User user1 = new User("272691", "Jan", "Kowalski", "jan.kowalski@example.com");
        User user2 = new User("327384", "Anna", "Nowak", "anna.nowak@example.com");
        User user3 = new User("836296", "Piotr", "Zieliński", "piotr.zielinski@example.com");

        userRepository.saveAll(List.of(
                user1, user2, user3
        ));

        Loan loan1 = new Loan(user1, book1, LocalDate.now(), LocalDate.now().plusDays(30));
        Loan loan2 = new Loan(user2, book2, LocalDate.now(), LocalDate.now().plusDays(30));
        Loan loan3 = new Loan(user3, book3, LocalDate.of(2025,2,14), LocalDate.of(2025,3,14));

        loanRepository.saveAll(List.of(loan1, loan2, loan3));
    }
}
