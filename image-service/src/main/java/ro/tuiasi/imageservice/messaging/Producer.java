package ro.tuiasi.imageservice.messaging;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ro.tuiasi.imageservice.messaging.ImageMess;


@Component
public class Producer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${jsa.rabbitmq.exchange}")
    private String exchange;

    @Value("${jsa.rabbitmq.routingkey}")
    private String routingkey;

    public void produce(ImageMess imageMess){
        amqpTemplate.convertAndSend(exchange, routingkey, imageMess);
        System.out.println("Send msg = " + imageMess);
    }
}