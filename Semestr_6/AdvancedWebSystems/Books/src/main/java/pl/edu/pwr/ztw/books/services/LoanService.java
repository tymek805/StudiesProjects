package pl.edu.pwr.ztw.books.services;
import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.models.Book;
import pl.edu.pwr.ztw.books.models.Loan;
import pl.edu.pwr.ztw.books.models.User;
import pl.edu.pwr.ztw.books.repositories.ILoanRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class LoanService implements ILoanService {

    private final ILoanRepository loanRepository;
    public LoanService(ILoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    private void updateFines()
    {
        loanRepository.findAll().forEach(Loan::calculateFine);
    }

    @Override
    public Collection<Loan> getLoans() {
        updateFines();
        return loanRepository.findAll();
    }

    @Override
    public Loan getLoan(Long id) {
        updateFines();
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<Loan> getUserLoans(User user) {
        updateFines();
        return loanRepository.findByUser(user);
    }

    @Override
    public Loan addLoan(Book book, User user) {
        if ( loanRepository.findAll().stream().filter(loan -> ( loan.getBook().equals(book) && loan.getReturnDate()==null )).toList().isEmpty() ) {
            Loan loan = new Loan();
            loan.setBook(book);
            loan.setUser(user);
            loan.setLoanDate(LocalDate.now());
            loan.setPlannedReturnDate(LocalDate.now().plusDays(30));

            return loanRepository.save(loan);
        }
        throw new IllegalStateException("Book is borrowed by another user");
    }

    @Override
    public Loan renewLoan(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        if (loan.getReturnDate()!=null) {
            throw new IllegalStateException("Book was already returned");
        }
        if (loan.getLoanDate().plusDays(59).isBefore(loan.getPlannedReturnDate())) {
            throw new IllegalStateException("Loan can not be renewed");
        }

        loan.setPlannedReturnDate(loan.getPlannedReturnDate().plusDays(30));

        return loanRepository.save(loan);
    }

    @Override
    public Loan returnLoan(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        if (loan.getReturnDate()!=null) {
            throw new IllegalStateException("Book was already returned");
        }
        loan.setReturnDate(LocalDate.now());

        return loanRepository.save(loan);
    }

    @Override
    public boolean deleteLoan(Long id) {
        if (loanRepository.existsById(id)) {
            loanRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
