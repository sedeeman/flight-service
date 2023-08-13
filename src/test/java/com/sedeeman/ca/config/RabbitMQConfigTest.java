package com.sedeeman.ca.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.FanoutExchange;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RabbitMQConfigTest {

    @InjectMocks
    private RabbitMQConfig rabbitMQConfig;

    @Test
    void testFlightStatusExchange() {
        FanoutExchange fanoutExchange = rabbitMQConfig.flightStatusExchange();
        assertNotNull(fanoutExchange);
    }


}