package com.krootix.config;

import com.krootix.common.model.ExchangeRate;
import com.krootix.common.model.ExchangeRateRu;
import com.krootix.common.service.ExchangeRateGenerator;
import com.krootix.common.service.ExchangeRateGeneratorImpl;
import com.krootix.common.service.ExchangeRateHolder;
import com.krootix.common.service.ExchangeRateHolderImpl;
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

import static com.krootix.common.model.AbstractExchangeRateRu.Names.RU_EU;

@Configuration
public class AppConfig {

    public static final String EX = "topic-exchange";
    public static final String Q2 = "ru-eu-topic-q2";
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
        rabbitTemplate.setRoutingKey(ROUTING_KEY_EU);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    ExchangeRateHolder<BigDecimal> ExchangeRateHolder() {
        ExchangeRate<BigDecimal> exchangeRate = new ExchangeRateRu(RU_EU.name(), BigDecimal.valueOf(78.9));
        return new ExchangeRateHolderImpl(exchangeRate);
    }

    @Bean
    ExchangeRateGenerator<BigDecimal> ExchangeRateGenerator() {
        return new ExchangeRateGeneratorImpl(65, 85);
    }

    @Bean
    ExchangeRate<BigDecimal> exchangeRate() {
        return new ExchangeRateRu(RU_EU.name(), BigDecimal.valueOf(0));
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EX);
    }

    @Bean
    Queue queue2() {
        return new Queue(Q2);
    }

    @Bean
    Binding binding2() {
        return BindingBuilder.bind(queue2()).to(topicExchange()).with(ROUTING_KEY_EU);
    }
}