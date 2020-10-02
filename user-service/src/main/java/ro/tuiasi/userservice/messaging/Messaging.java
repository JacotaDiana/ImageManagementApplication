package ro.tuiasi.userservice.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Messaging {

    enum MessageActions {ADD, DELETE};

    @Output("UserImageChannel")
    MessageChannel UserImage();

    @Output("UserMessageChannel")
    MessageChannel UserMessage();

    @Output("UserNotificationChannel")
    MessageChannel UserNotificationChannel();
}
