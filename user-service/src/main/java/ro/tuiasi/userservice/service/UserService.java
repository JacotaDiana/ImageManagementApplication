package ro.tuiasi.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.tuiasi.userservice.messaging.Messaging;
import ro.tuiasi.userservice.messaging.UserMess;
import ro.tuiasi.userservice.models.User;
import ro.tuiasi.userservice.payload.request.RegisterRequest;
import ro.tuiasi.userservice.payload.request.UpdateRequest;
import ro.tuiasi.userservice.payload.response.MessageResponse;
import ro.tuiasi.userservice.repository.UserRepository;
import ro.tuiasi.userservice.service.AES;
import ro.tuiasi.userservice.service.ImageCompression;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    Messaging messaging;

    public ResponseEntity<?> registerUser(RegisterRequest registerRequest) throws InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException, IOException {
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }
        //TransactionDefinition definition = new DefaultTransactionDefinition();
        //TransactionStatus status = transactionManager.getTransaction(definition);
        //try {

        try {
            registerRequest.setPassword(bcryptEncoder.encode(registerRequest.getPassword()));
            if (registerRequest.getProfilePic() != null) {
                byte[] profilePic = registerRequest.getProfilePic();
                profilePic = ImageCompression.compressBytes(profilePic);
                //registerRequest.setProfilePic(AES.encryptData(registerRequest.getPassword(), profilePic));
                registerRequest.setProfilePic(profilePic);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: User couldn't be saved. Please try again!"));
        }

        User user = new User( registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getPassword(), registerRequest.getUsername(), registerRequest.getProfilePic());

        //logger.warn(user.toString());
        try{
            userRepository.save(user);

            User u = userRepository.findByUsername(user.getUsername()).get();
            //messsaging
            UserMess userMess = new UserMess();
            userMess.setUserId(u.getId());
            userMess.setUsername(u.getUsername());

            //sendToImage(userMess, "addUser");
            sendToImage(userMess, 75);
            sendToMessage(userMess, 75);
            sendToNotification(userMess, 75);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: User couldn't be saved. Please try again!"));
        }
        //transactionManager.commit(status);
        //  logger.warn("Is rollbackOnly: " + TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<?> deleteUser(int userId){
        User user = userRepository.findById(userId).get();
        if (user != null) {
            try {
                userRepository.delete(user);
                sendToImage(new UserMess(user.getId(), user.getUsername()), 25);
                sendToMessage(new UserMess(user.getId(), user.getUsername()), 25);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Error: User couldn't be deleted!"));
            }
        }
        return ResponseEntity.ok(new MessageResponse("User successfully removed!"));
    }

    public ResponseEntity<?> updateUser(UpdateRequest updateRequest){
        User user = userRepository.findById(updateRequest.getId()).get();
        if (user != null) {
            try {
                if(updateRequest.getPassword().length() > 5){
                    user.setPassword(bcryptEncoder.encode(updateRequest.getPassword()));
                }
                if(updateRequest.getProfilePic() != null) {
                    byte[] profilePic = updateRequest.getProfilePic();
                    profilePic = ImageCompression.compressBytes(profilePic);
                    updateRequest.setProfilePic(profilePic);
                    user.setProfilePic(updateRequest.getProfilePic());
                }
                user.setLastName(updateRequest.getLastName());
                user.setFirstName(updateRequest.getFirstName());
                userRepository.save(user);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Error: Couldn't upload user. Please try again!"));
            }
        }
        return ResponseEntity.ok(new MessageResponse("User successfully updated!"));
    }

    public String sendToImage(UserMess userMess, Integer method) {
        messaging.UserImage().send(MessageBuilder.withPayload(userMess).setHeader("method", method).build());
        return "Success";
    }

    public String sendToMessage(UserMess userMess, Integer method) {
        messaging.UserMessage().send(MessageBuilder.withPayload(userMess).setHeader("method", method).build());
        return "Success";
    }

    public String sendToNotification(UserMess userMess, Integer method) {
        messaging.UserNotificationChannel().send(MessageBuilder.withPayload(userMess).setHeader("method", method).build());
        return "Success";
    }
}
