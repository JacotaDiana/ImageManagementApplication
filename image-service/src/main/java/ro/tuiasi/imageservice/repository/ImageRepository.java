package ro.tuiasi.imageservice.repository;

import org.springframework.data.repository.CrudRepository;
import ro.tuiasi.imageservice.models.Image;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends CrudRepository<Image, Long> {
    List<Image> findByUserId(int userId);
    Optional<Image> findByIdAndUserId(Long id, int userId);
}
