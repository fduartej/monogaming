package gob.sunat.apisunat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class RabbitConfig {
    
    @Value("${sunat.rabbitmq.exchange}")
    private String EXCHANGE_NAME;
    @Value("${sunat.rabbitmq.queue}")
    private String QUEUE_NAME;

    @Bean
    public Queue createQueue() {
      return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Exchange fanoutExchange() {
      return new FanoutExchange(EXCHANGE_NAME, true, false);
    }
    
    @Bean
    public Binding queueBinding() {
      return new Binding(QUEUE_NAME, Binding.DestinationType.QUEUE, EXCHANGE_NAME, "", null);
    }
}