package ro.tuiasi.messageservice.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Messaging {

    String USER = "UserMessageChannel";

    @Input(USER)
    MessageChannel UserMessage();

    @Output("MessageNotificationChannel")
    MessageChannel NotificationMessageChannel();
}
