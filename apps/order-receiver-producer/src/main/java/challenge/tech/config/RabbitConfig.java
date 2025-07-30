package challenge.tech.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${application.queue.order-receiver}")
    private String orderReceiverQueueName;

    @Bean
    public Queue orderReceiverQueue() {
        return QueueBuilder.durable(orderReceiverQueueName).build();
    }
}