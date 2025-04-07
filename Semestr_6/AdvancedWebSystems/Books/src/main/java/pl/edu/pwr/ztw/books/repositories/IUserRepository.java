package pl.edu.pwr.ztw.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.ztw.books.models.User;

public interface IUserRepository extends JpaRepository<User, Long> {
}