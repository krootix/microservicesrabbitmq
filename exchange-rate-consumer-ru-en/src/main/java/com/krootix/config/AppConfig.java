package com.krootix.config;

import com.krootix.model.ExchangeRate;
import com.krootix.model.ExchangeRateRuEn;
import com.krootix.service.ExchangeRateGenerator;
import com.krootix.service.ExchangeRateGeneratorImpl;
import com.krootix.service.ExchangeRateHolder;
import com.krootix.service.ExchangeRateHolderImpl;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

import static com.krootix.model.AbstractExchangeRate.Names.RU_EN;

@Configuration
public class AppConfig {

    public static final String EX = "topic-exchange";
    public static final String Q1 = "ru-en-topic-q1";
    public static final String ROUTING_KEY_EN = "rateRuEn.#";
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
    public SimpleRabbitListenerContainerFactory listenerContainerFactory(ConnectionFactory connectionFactory,
                                                                         SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange(EX);
        rabbitTemplate.setRoutingKey(ROUTING_KEY_EN);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    ExchangeRateHolder ExchangeRateHolder() {
        ExchangeRate<BigDecimal> exchangeRate = new ExchangeRateRuEn(RU_EN.name(), BigDecimal.valueOf(78.9));
        return new ExchangeRateHolderImpl(exchangeRate);
    }

    @Bean
    ExchangeRateGenerator ExchangeRateGenerator() {
        return new ExchangeRateGeneratorImpl(65, 85);
    }

    @Bean
    ExchangeRate<BigDecimal> exchangeRate() {
        return new ExchangeRateRuEn(RU_EN.name(), BigDecimal.valueOf(0));
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
    Binding binding1() {
        return BindingBuilder.bind(queue1()).to(topicExchange()).with(ROUTING_KEY_EN);
    }
}