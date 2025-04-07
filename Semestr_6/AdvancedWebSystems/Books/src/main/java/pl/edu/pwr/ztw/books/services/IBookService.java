package pl.edu.pwr.ztw.books.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pwr.ztw.books.dto.BookDTO;
import pl.edu.pwr.ztw.books.models.Book;

public interface IBookService {
    Page<Book> getBooks(Pageable pageable);
    Book getBook(Long id);
    Book addBook(BookDTO bookDTO);
    Book updateBook(Long id, BookDTO bookDTO);
    boolean deleteBook(Long id);
}
