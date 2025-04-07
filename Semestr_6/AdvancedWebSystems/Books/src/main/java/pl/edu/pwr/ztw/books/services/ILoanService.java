package pl.edu.pwr.ztw.books.services;

import pl.edu.pwr.ztw.books.models.Book;
import pl.edu.pwr.ztw.books.models.Loan;
import pl.edu.pwr.ztw.books.models.User;

import java.util.Collection;

public interface ILoanService {
    Collection<Loan> getLoans();
    Loan getLoan(Long id);
    Collection<Loan> getUserLoans(User user);
    Loan addLoan(Book book, User user);
    Loan renewLoan(Long id);
    Loan returnLoan(Long id);
    boolean deleteLoan(Long id);
}
