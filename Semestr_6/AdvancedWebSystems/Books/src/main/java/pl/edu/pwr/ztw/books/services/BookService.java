package pl.edu.pwr.ztw.books.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.dto.BookDTO;
import pl.edu.pwr.ztw.books.models.Author;
import pl.edu.pwr.ztw.books.models.Book;
import pl.edu.pwr.ztw.books.repositories.IAuthorRepository;
import pl.edu.pwr.ztw.books.repositories.IBookRepository;

@Service
public class BookService implements IBookService {
    private final IBookRepository bookRepository;
    private final IAuthorRepository authorRepository;

    public BookService(IBookRepository bookRepository, IAuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Page<Book> getBooks(Pageable pageable){
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book addBook(BookDTO bookDTO) {
        Author author = authorRepository.findById(bookDTO.getAuthorId()).orElse(null);
        if (author == null || bookDTO.getTitle() == null || bookDTO.getPages() == null)
            return null;

        Book book = new Book(bookDTO.getTitle(), author, bookDTO.getPages());
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id,  BookDTO bookDTO) {
        Book updateBook = bookRepository.findById(id).orElse(null);

        if (updateBook == null)
            return null;

        Long authorId = bookDTO.getAuthorId();
        if (authorId != null) {
            Author author = authorRepository.findById(bookDTO.getAuthorId()).orElse(null);
            if (author == null) {
                return null;
            }
            updateBook.setAuthor(author);
        }

        if (bookDTO.getTitle() != null)
            updateBook.setTitle(bookDTO.getTitle());

        if (bookDTO.getPages() != null)
            updateBook.setPages(bookDTO.getPages());

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
