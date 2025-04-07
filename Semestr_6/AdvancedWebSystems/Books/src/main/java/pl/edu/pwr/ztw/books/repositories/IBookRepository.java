package pl.edu.pwr.ztw.books.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.ztw.books.models.Book;

public interface IBookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAll(Pageable pageable);
}
