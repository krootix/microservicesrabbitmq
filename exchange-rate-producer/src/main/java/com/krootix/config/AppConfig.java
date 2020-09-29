package com.krootix.config;

import com.krootix.common.model.ExchangeRate;
import com.krootix.common.model.ExchangeRateRu;
import com.krootix.common.service.ExchangeRateGenerator;
import com.krootix.common.service.ExchangeRateGeneratorImpl;
import com.krootix.common.service.ExchangeRateHolder;
import com.krootix.common.service.ExchangeRateHolderImpl;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

import static com.krootix.common.model.AbstractExchangeRateRu.Names.RU_EN;

@Configuration
public class AppConfig {

    public static final String EX = "topic-exchange";

    static final String Q1 = "ru-en-topic-q1";
    static final String Q2 = "ru-eu-topic-q2";

    public static final String ROUTING_KEY_EN = "rateRuEn.#";
    public static final String ROUTING_KEY_EU = "rateRuEu.#";

    private static final String HOST = "localhost";

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(HOST);
    }

    @Bean("amqp")
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange(EX);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    ExchangeRateHolder<BigDecimal> ExchangeRateHolder() {
        ExchangeRate<BigDecimal> exchangeRate = new ExchangeRateRu(RU_EN.name(), BigDecimal.valueOf(78.9));
        return new ExchangeRateHolderImpl(exchangeRate);
    }

    @Bean
    ExchangeRateGenerator<BigDecimal> ExchangeRateGenerator() {
        return new ExchangeRateGeneratorImpl(65, 85);
    }

    @Bean
    ExchangeRate<BigDecimal> exchangeRate() {
        return new ExchangeRateRu(RU_EN.name(), BigDecimal.valueOf(0));
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EX);
    }

    @Bean
    Queue queue1() {
        return new Queue(Q1);
    }

    @Bean
    Queue queue2() {
        return new Queue(Q2);
    }

    @Bean
    Binding binding1() {
        return BindingBuilder.bind(queue1()).to(topicExchange()).with(ROUTING_KEY_EN);
    }

    @Bean
    Binding binding2() {
        return BindingBuilder.bind(queue2()).to(topicExchange()).with(ROUTING_KEY_EU);
    }
}