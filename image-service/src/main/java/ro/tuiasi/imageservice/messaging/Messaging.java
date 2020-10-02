package ro.tuiasi.imageservice.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Messaging {

    @Input("UserImageChannel")
    MessageChannel UserImage();

    @Output("ImageMessageChannel")
    MessageChannel ImageMessage();
}
