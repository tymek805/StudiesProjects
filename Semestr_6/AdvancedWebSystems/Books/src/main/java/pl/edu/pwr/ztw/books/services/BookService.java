package pl.edu.pwr.ztw.books.services;

import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.models.Book;
import pl.edu.pwr.ztw.books.repositories.IBookRepository;

import java.util.Collection;

@Service
public class BookService implements IBookService {
    private final IBookRepository bookRepository;

    public BookService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Collection<Book> getBooks(){
        return bookRepository.findAll();
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        Book updateBook = bookRepository.findById(id).orElse(null);

        if (updateBook == null)
            return null;

        updateBook.setAuthor(book.getAuthor());
        updateBook.setTitle(book.getTitle());
        updateBook.setPages(book.getPages());

        return bookRepository.save(updateBook);
    }

    @Override
    public boolean deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null)
            return false;

        bookRepository.deleteById(id);
        return true;
    }
}
