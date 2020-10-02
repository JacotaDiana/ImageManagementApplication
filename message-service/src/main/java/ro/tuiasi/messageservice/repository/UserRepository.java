package ro.tuiasi.messageservice.repository;

import org.springframework.data.repository.CrudRepository;
import ro.tuiasi.messageservice.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
