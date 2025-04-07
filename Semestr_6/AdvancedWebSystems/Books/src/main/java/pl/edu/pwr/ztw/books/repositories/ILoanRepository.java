package pl.edu.pwr.ztw.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.ztw.books.models.Loan;
import pl.edu.pwr.ztw.books.models.User;

import java.util.Collection;
import java.util.List;

public interface ILoanRepository extends JpaRepository<Loan, Long> {
    Collection<Loan> findByUser(User user);
}