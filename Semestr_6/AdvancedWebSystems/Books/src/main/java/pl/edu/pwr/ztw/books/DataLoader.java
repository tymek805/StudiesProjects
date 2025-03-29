package pl.edu.pwr.ztw.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.edu.pwr.ztw.books.models.Author;
import pl.edu.pwr.ztw.books.models.Book;
import pl.edu.pwr.ztw.books.repositories.IAuthorRepository;
import pl.edu.pwr.ztw.books.repositories.IBookRepository;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final IBookRepository bookRepository;
    private final IAuthorRepository authorRepository;

    public DataLoader(IBookRepository bookRepository, IAuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
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
    }
}
