package ro.tuiasi.messageservice.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.annotation.EnableBinding;
import ro.tuiasi.messageservice.services.MessageService;

@EnableBinding(Messaging.class)
public class ReceiveMessage {
    @Autowired
    MessageService messageService;

    //addUser
    @StreamListener(target = "UserMessageChannel", condition = "headers['method'] > 50")
    public void processUserAdd(UserMess userMess) {
        messageService.addUser(userMess.getUserId(), userMess.getUsername());
        System.out.println(userMess.toString());
    }

    //deleteUser
    @StreamListener(target = "UserMessageChannel", condition = "headers['method'] < 50")
    public void processUserDelete(UserMess userMess) {
        messageService.deleteUser(userMess.getUserId());
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


        public String getUsername() {
            return username;
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
