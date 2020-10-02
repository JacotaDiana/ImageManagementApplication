package ro.tuiasi.apigateway.repository;

import org.springframework.data.repository.CrudRepository;
import ro.tuiasi.apigateway.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findById(Integer id);
    Optional<User> findByUsername(String username);
}
