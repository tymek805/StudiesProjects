package pl.edu.pwr.ztw.books.services;

import pl.edu.pwr.ztw.books.dto.BookDTO;
import pl.edu.pwr.ztw.books.models.Book;

import java.util.Collection;

public interface IBookService {
    Collection<Book> getBooks();
    Book getBook(Long id);
    Book addBook(BookDTO bookDTO);
    Book updateBook(Long id, BookDTO bookDTO);
    boolean deleteBook(Long id);
}
