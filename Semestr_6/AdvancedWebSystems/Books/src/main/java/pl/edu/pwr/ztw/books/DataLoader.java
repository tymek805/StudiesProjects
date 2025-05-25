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
        Author author4 = new Author("Bolesław", "Prus");
        Author author5 = new Author("Eliza", "Orzeszkowa");

        authorRepository.saveAll(List.of(
                author1, author2, author3, author4, author5
        ));

        Book book1 = new Book("Potop", author1, 936);
        Book book2 = new Book("Ogniem i mieczem", author1, 720);
        Book book3 = new Book("Quo Vadis", author1, 640);

        Book book4 = new Book("Chłopi", author2, 840);
        Book book5 = new Book("Ziemia obiecana", author2, 432);

        Book book6 = new Book("Dziady", author3, 292);
        Book book7 = new Book("Pan Tadeusz", author3, 340);

        Book book8 = new Book("Lalka", author4, 880);
        Book book9 = new Book("Faraon", author4, 560);

        Book book10 = new Book("Nad Niemnem", author5, 480);

        bookRepository.saveAll(List.of(
                book1, book2, book3, book4, book5,
                book6, book7, book8, book9, book10
        ));

//        User user1 = new User("272691", "Jan", "Kowalski", "jan.kowalski@example.com");
//        User user2 = new User("327384", "Anna", "Nowak", "anna.nowak@example.com");
//        User user3 = new User("836296", "Piotr", "Zieliński", "piotr.zielinski@example.com");
//
//        userRepository.saveAll(List.of(
//                user1, user2, user3
//        ));
//
//        Loan loan1 = new Loan(user1, book1, LocalDate.now(), LocalDate.now().plusDays(30));
//        Loan loan2 = new Loan(user2, book2, LocalDate.now(), LocalDate.now().plusDays(30));
//        Loan loan3 = new Loan(user3, book3, LocalDate.of(2025,2,14), LocalDate.of(2025,3,14));
//
//        loanRepository.saveAll(List.of(loan1, loan2, loan3));
    }
}
