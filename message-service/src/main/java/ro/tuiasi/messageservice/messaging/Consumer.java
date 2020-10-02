package ro.tuiasi.messageservice.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.tuiasi.messageservice.services.ImageService;

@Component
public class Consumer {

    @Autowired
    ImageService imageService;

    @RabbitListener(queues="${jsa.rabbitmq.queue}", containerFactory="jsaFactory")
    public void recievedMessage(ImageMess imageMess) {
        imageService.addImages(imageMess);
    }
}
