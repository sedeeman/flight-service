package com.sedeeman.ca.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange flightStatusExchange() {
        return new FanoutExchange("flight-status-exchange");
    }

}
