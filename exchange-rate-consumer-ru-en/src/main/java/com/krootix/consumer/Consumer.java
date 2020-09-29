package com.krootix.consumer;

import com.krootix.config.AppConfig;
import com.krootix.model.ExchangeRate;
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

import static com.krootix.config.AppConfig.Q1;

@EnableRabbit //need to activate processing annotations @RabbitListener
@Component
@RabbitListener(queues = AppConfig.Q1, containerFactory = "listenerContainerFactory")
//@RabbitListener(bindings = @QueueBinding(value = @Queue(value = Q1, durable = "true"),
//        exchange = @Exchange(value = AppConfig.EX, ignoreDeclarationExceptions = "true"),
//        key = AppConfig.ROUTING_KEY_EN))
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final RabbitTemplate rabbitTemplate;
    private final ExchangeRateHolder exchangeRateHolder;

    @Autowired
    public Consumer(RabbitTemplate rabbitTemplate, ExchangeRateHolder exchangeRateHolder) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeRateHolder = exchangeRateHolder;
    }

    @RabbitHandler
    public void receivedMessage(ExchangeRate<BigDecimal> exchangeRate) {
        exchangeRateHolder.update(exchangeRate);

        logger.info("Received from queue " + Q1 + ": {}", exchangeRateHolder.getRate());
    }
}