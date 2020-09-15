package org.jeecg.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class RabbitConfig {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /*@Bean
    public Queue printQueue() {
        return new Queue("printQueue");
    }*/

    @Bean
    public Queue cardQueue() {
        return new Queue("cardQueue");
    }

    @Bean
    public Queue stayAlertQueue() {
        return new Queue("stayAlertQueue");
    }


    @Bean
    public Queue checkinQueue() {
        return new Queue("checkinQueue");
    }

    @Bean
    public Queue checkoutQueue() {
        return new Queue("checkoutQueue");
    }

}
