package ro.tuiasi.imageservice.repository;

import org.springframework.data.repository.CrudRepository;
import ro.tuiasi.imageservice.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
