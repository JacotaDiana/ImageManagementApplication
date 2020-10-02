package ro.tuiasi.imageservice.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import ro.tuiasi.imageservice.service.ImageService;
import ro.tuiasi.imageservice.service.UserService;

@EnableBinding(Sink.class)
public class ReceiveFromUser {

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    //addUser
    @StreamListener(target = "UserImageChannel", condition = "headers['method'] > 50")
    public void processUserAdd(UserMess userMess) {
        userService.addUser(userMess.getUserId());
        System.out.println(userMess.toString());
    }

    //deleteUser
    @StreamListener(target = "UserImageChannel", condition = "headers['method'] < 50")
    public void processUserDelete(UserMess userMess) {
        imageService.deleteUserAndImages(userMess.getUserId());
        System.out.println(userMess.toString());
    }

    static class UserMess {
        private int userId;
        private String username;

        public UserMess() {
        }

        public UserMess(int userId, String username) {
            this.userId = userId;
            this.username = username;
        }

        public int getUserId() {
            return userId;
        }

        @Override
        public String toString() {
            return "UserMess{" +
                    "userId=" + userId +
                    ", username='" + username + '\'' +
                    '}';
        }
    }
}
