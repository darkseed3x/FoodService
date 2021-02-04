package ru.vvzl.fs.fs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
@ConfigurationProperties("service.rabbit")
public class RabbitConfig {

    @Value("${queueOut:testOut}")
    private String queueOut;

    @Value("${queueErr:testErr}")
    private String queueErr;

    @Value("${exchange:testExc}")
    private String exchange;

    @Value("${routingKeyOut:testRkOut}")
    private String routingKeyOut;

    @Value("${routingKeyErr:testRkErr}")
    private String routingKeyErr;

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue queueOut() {
        return new Queue(queueOut);
    }
    @Bean
    public Queue queueErr() {
        return new Queue(queueErr);
    }

    @Bean
    Binding bindingErr(Queue queueErr, TopicExchange exchange) {
        return BindingBuilder.bind(queueErr).to(exchange).with(routingKeyErr);
    }

    @Bean
    Binding bindingOut(Queue queueOut, TopicExchange exchange) {
        return BindingBuilder.bind(queueOut).to(exchange).with(routingKeyOut);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonConverter());
        return rabbitTemplate;
    }


    @Bean
    public MessageConverter jsonConverter() {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter();
    }


    public String getQueueOut() {
        return queueOut;
    }

    public void setQueueOut(String queueOut) {
        this.queueOut = queueOut;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }


    public String getRoutingKeyOut() {
        return routingKeyOut;
    }

    public void setRoutingKeyOut(String routingKeyOut) {
        this.routingKeyOut = routingKeyOut;
    }

    public String getQueueErr() {
        return queueErr;
    }

    public void setQueueErr(String queueErr) {
        this.queueErr = queueErr;
    }

    public String getRoutingKeyErr() {
        return routingKeyErr;
    }

    public void setRoutingKeyErr(String routingKeyErr) {
        this.routingKeyErr = routingKeyErr;
    }
}