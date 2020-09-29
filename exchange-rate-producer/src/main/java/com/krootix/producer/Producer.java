package com.krootix.producer;

import com.krootix.config.AppConfig;
import com.krootix.service.ExchangeRateGenerator;
import com.krootix.service.ExchangeRateHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.krootix.config.AppConfig.ROUTING_KEY_EN;
import static com.krootix.config.AppConfig.ROUTING_KEY_EU;
import static com.krootix.model.AbstractExchangeRate.Names.RU_EN;
import static com.krootix.model.AbstractExchangeRate.Names.RU_EU;

@Component
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    private final RabbitTemplate rabbitTemplate;
    private final ExchangeRateHolder exchangeRateHolder;
    private final ExchangeRateGenerator exchangeRateGenerator;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate, ExchangeRateHolder exchangeRateHolder, ExchangeRateGenerator exchangeRateGenerator) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeRateHolder = exchangeRateHolder;
        this.exchangeRateGenerator = exchangeRateGenerator;
    }

    @Scheduled(fixedDelay = 2000L)
    public void produce() {

        exchangeRateHolder.update(exchangeRateGenerator.generate());
        var rate = exchangeRateHolder.getRate();

        if (rate.getName().equals(RU_EN.name()))
            rabbitTemplate.convertAndSend(AppConfig.EX, ROUTING_KEY_EN, exchangeRateHolder.getRate());
        if (rate.getName().equals(RU_EU.name()))
            rabbitTemplate.convertAndSend(AppConfig.EX, ROUTING_KEY_EU, exchangeRateHolder.getRate());

        logger.info("sending rate {} with value: {}", rate.getName(), rate.getRate());
    }
}