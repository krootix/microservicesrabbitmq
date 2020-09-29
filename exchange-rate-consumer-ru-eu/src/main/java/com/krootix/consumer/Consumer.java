package com.krootix.consumer;

import com.krootix.model.ExchangeRate;
import com.krootix.service.ExchangeRateGenerator;
import com.krootix.service.ExchangeRateHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.krootix.config.AppConfig.Q2;

@EnableRabbit //need to activate processing annotations @RabbitListener
@Component
@RabbitListener(queues = Q2, containerFactory = "listenerContainerFactory")
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final RabbitTemplate rabbitTemplate;
    private final ExchangeRateHolder exchangeRateHolder;

    @Autowired
    public Consumer(RabbitTemplate rabbitTemplate, ExchangeRateHolder exchangeRateHolder, ExchangeRateGenerator exchangeRateGenerator) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeRateHolder = exchangeRateHolder;
    }

    @RabbitHandler
    public void receivedMessage(ExchangeRate<BigDecimal> exchangeRate) {
        exchangeRateHolder.update(exchangeRate);

        logger.info("Received from queue " + Q2 + ": {}", exchangeRateHolder.getRate());
    }
}