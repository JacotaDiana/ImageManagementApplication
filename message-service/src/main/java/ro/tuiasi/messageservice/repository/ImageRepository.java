package ro.tuiasi.messageservice.repository;

import org.springframework.data.repository.CrudRepository;
import ro.tuiasi.messageservice.models.Image;
import ro.tuiasi.messageservice.models.Message;

import java.util.List;

public interface ImageRepository extends CrudRepository<Image, Long> {
    List<Image> findByMessages(Message message);
}
