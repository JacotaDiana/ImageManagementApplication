package ro.tuiasi.messageservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.tuiasi.messageservice.models.Message;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository <Message, Long> {
    ArrayList<Message> findByReceiverId(int receiverId);
    Optional<Message> findByReceiverIdAndId(int userReceiver, long messageId);
}
