package ro.tuiasi.messageservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ro.tuiasi.messageservice.models.Message;
import ro.tuiasi.messageservice.messaging.ImageCustomized;
import ro.tuiasi.messageservice.messaging.Messaging;
import ro.tuiasi.messageservice.messaging.NotificationMessage;
import ro.tuiasi.messageservice.models.Image;
import ro.tuiasi.messageservice.models.User;
import ro.tuiasi.messageservice.payload.AllMessagesResponse;
import ro.tuiasi.messageservice.payload.ImageResponse;
import ro.tuiasi.messageservice.repository.ImageRepository;
import ro.tuiasi.messageservice.repository.MessageRepository;
import ro.tuiasi.messageservice.repository.UserRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@Component
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    Messaging messaging;

   public void addUser(int userId, String username) {
       User user = new User();
       user.setId(userId);
       user.setUsername(username);
       try {
           userRepository.save(user);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

   public void deleteUser(int userId) {
       userRepository.deleteById(userId);
   }

    public void addImages(List<ImageCustomized> listImages) {
        for(ImageCustomized img : listImages) {
            Image image = new Image();
            image.setId(img.getId());
            image.setImage(img.getImageBytes());

            try {
                imageRepository.save(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<AllMessagesResponse> getAllMessagesByUserId(int receiverId) {
        List<Message> retrivedMessages = messageRepository.findByReceiverId(receiverId);
        List<AllMessagesResponse> newList = new ArrayList<>();
        for(Message msg : retrivedMessages) {
            newList.add(new AllMessagesResponse(msg.getId(), msg.getSender().getUsername(), msg.getTitle()));
        }
        return newList;
    }

   public List<ImageResponse> getMessage(long messageId, int userId) {
        Message msg = messageRepository.findById(messageId).get();//todo de bagat si userId pe undeva

        List<Image> images = imageRepository.findByMessages(msg);

       List<ImageResponse> newList = new ArrayList<>();

       for(Image image : images) {
           newList.add(new ImageResponse(image.getId(),decompressBytes(image.getImage())));
       }
       return newList;
   }

    public ResponseEntity<?> deleteMessage(long messageId, int userId) {
        Optional<Message> message = messageRepository.findByReceiverIdAndId(userId, messageId);
        if(!message.isPresent()){
            return ResponseEntity.badRequest()
                    .body("Error: Couldn't delete message!");
        }
        for(Image image: message.get().getImages()){
            message.get().removeImages(image);
        }
        messageRepository.delete(message.get());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error");
    }

    public ResponseEntity<?> sendMessage(long[] imagesIds, int userId, String title, String messageBody, String receiverUsername) throws IOException {
        User receiver = userRepository.findByUsername(receiverUsername).get();
        if(receiver == null) {
            return ResponseEntity.badRequest()
                    .body("Error: Email is already taken!");
        }
        User sender = userRepository.findById(userId).get();

        try {
            Message message = new Message(sender, receiver, title, messageBody, new Date(System.currentTimeMillis()));

            for(int i = 0; i < imagesIds.length; i++) {
                Image image = imageRepository.findById(imagesIds[i]).get();
                if(image != null) {
                    message.getImages().add(image);
                }
            }

            for(int i = 0; i < imagesIds.length; i++) {
                Image image = imageRepository.findById(imagesIds[i]).get();
                if(image != null) {
                    image.getMessages().add(message);
                }
            }
            Message mess = messageRepository.save(message);
            NotificationMessage notificationMessage = new NotificationMessage();
            notificationMessage.setMessageId(mess.getId());
            notificationMessage.setSenderId(sender.getId());
            notificationMessage.setReceiverId(receiver.getId());
            sendToNotification(notificationMessage);
            return ResponseEntity.status(HttpStatus.OK).body("Uploaded the message successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error");
        }
    }
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

    public String sendToNotification(NotificationMessage notificationMessage) {
        messaging.NotificationMessageChannel().send(MessageBuilder.withPayload(notificationMessage).setHeader("method", 75).build());
        return "Success";
    }
}
