//package ru.vvzl.fs.fs.config;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@RefreshScope
//@ConfigurationProperties("service.foodService.rabbit")
//public class RabbitConfig {
//
//    @Value("${queueIn:testIn}")
//    private String queueIn;
//
//    @Value("${queueOut:testOut}")
//    private String queueOut;
//
//    @Value("${exchangeIn:testExcIn}")
//    private String exchangeIn;
//
//    @Value("${exchangeOut:testExcOut}")
//    private String exchangeOut;
//
//    @Value("${routingKeyIn:testRkIn}")
//    private String routingKeyIn;
//
//    @Value("${routingKeyOut:testRkOut}")
//    private String routingKeyOut;
//
//    @Bean
//    public Queue queueIn() {
//        return new Queue(queueIn);
//    }
//
//    @Bean
//    TopicExchange exchangeIn() {
//        return new TopicExchange(exchangeIn);
//    }
//
//    @Bean
//    Binding bindingIn(Queue queueIn, TopicExchange exchangeIn) {
//        return BindingBuilder.bind(queueIn).to(exchangeIn).with(routingKeyIn);
//    }
//
//    @Bean
//    public Queue queueOut() {
//        return new Queue(queueOut);
//    }
//
//    @Bean
//    TopicExchange exchangeOut() {
//        return new TopicExchange(exchangeOut);
//    }
//
//    @Bean
//    Binding bindingOut(Queue queueOut, TopicExchange exchangeOut) {
//        return BindingBuilder.bind(queueOut).to(exchangeOut).with(routingKeyOut);
//    }
//}