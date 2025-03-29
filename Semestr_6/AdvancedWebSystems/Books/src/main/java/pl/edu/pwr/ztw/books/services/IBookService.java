package pl.edu.pwr.ztw.books.services;

import pl.edu.pwr.ztw.books.models.Book;

import java.util.Collection;

public interface IBookService {
    Collection<Book> getBooks();
    Book getBook(Long id);
    Book addBook(Book book);
    Book updateBook(Long id, Book book);
    boolean deleteBook(Long id);
}
