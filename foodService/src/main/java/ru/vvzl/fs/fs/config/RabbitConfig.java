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

    @Value("${queueGetOrderIn:testGetOrderIn}")
    private String queueGetOrderIn;

    @Value("${queueNewOrderIn:testNewOrderIn}")
    private String queueNewOrderIn;

    @Value("${queueGetMenuIn:testGetMenuIn}")
    private String queueGetMenuIn;

    @Value("${queueGetRestIn:testGetRestIn}")
    private String queueGetRestIn;

    @Value("${queueGetAllMenuIn:testGetAllMenuIn}")
    private String queueGetAllMenuIn;

    @Value("${routingKeyGetAllMenuIn:testGetAllMenuIn}")
    private String routingKeyGetAllMenuIn;

    @Value("${routingKeyGetRestIn:testGetRestIn}")
    private String routingKeyGetRestIn;

    @Value("${routingKeyGetMenuIn:testGetMenuIn}")
    private String routingKeyGetMenuIn;

    @Value("${routingKeyNewOrderIn:testNewOrderIn}")
    private String routingKeyNewOrderIn;

    @Value("${routingKeyGetOrderIn:testGetOrderIn}")
    private String routingKeyGetOrderIn;


    @Value("${queueGetOrderOut:testGetOrderOut}")
    private String queueGetOrderOut;

    @Value("${queueNewOrderOut:testNewOrderOut}")
    private String queueNewOrderOut;

    @Value("${queueGetMenuOut:testGetMenuOut}")
    private String queueGetMenuOut;

    @Value("${queueGetRestOut:testGetRestOut}")
    private String queueGetRestOut;

    @Value("${queueGetAllMenuOut:testGetAllMenuOut}")
    private String queueGetAllMenuOut;

    @Value("${routingKeyGetAllMenuOut:testGetAllMenuOut}")
    private String routingKeyGetAllMenuOut;

    @Value("${routingKeyGetRestOut:testGetRestOut}")
    private String routingKeyGetRestOut;

    @Value("${routingKeyGetMenuOut:testGetMenuOut}")
    private String routingKeyGetMenuOut;

    @Value("${routingKeyNewOrderOut:testNewOrderOut}")
    private String routingKeyNewOrderOut;

    @Value("${routingKeyGetOrderOut:testGetOrderOut}")
    private String routingKeyGetOrderOut;


    @Value("${routingKeyErr:testRkErr}")
    private String routingKeyErr;

    @Value("${queueErr:testErr}")
    private String queueErr;

    @Value("${exchange:testExc}")
    private String exchange;

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue queueGetOrderOut() {
        return new Queue(queueGetOrderOut);
    }

    @Bean
    public Queue queueNewOrderOut() {
        return new Queue(queueNewOrderOut);
    }

    @Bean
    public Queue queueGetMenuOut() {
        return new Queue(queueGetMenuOut);
    }

    @Bean
    public Queue queueGetRestOut() {
        return new Queue(queueGetRestOut);
    }

    @Bean
    public Queue queueGetAllMenuOut() {
        return new Queue(queueGetAllMenuOut);
    }

    @Bean
    public Queue queueGetOrderIn() {
        return new Queue(queueGetOrderIn);
    }

    @Bean
    public Queue queueNewOrderIn() {
        return new Queue(queueNewOrderIn);
    }

    @Bean
    public Queue queueGetMenuIn() {
        return new Queue(queueGetMenuIn);
    }
    @Bean
    public Queue queueGetRestIn() {
        return new Queue(queueGetRestIn);
    }

    @Bean
    public Queue queueGetAllMenuIn() {
        return new Queue(queueGetAllMenuIn);
    }
    @Bean
    public Queue queueErr() {
        return new Queue(queueErr);
    }

    @Bean
    Binding bindingGetOrderIn(Queue queueGetOrderIn, TopicExchange exchange) {
        return BindingBuilder.bind(queueGetOrderIn).to(exchange).with(routingKeyGetOrderIn);
    }

    @Bean
    Binding bindingNewOrderIn(Queue queueNewOrderIn, TopicExchange exchange) {
        return BindingBuilder.bind(queueNewOrderIn).to(exchange).with(routingKeyNewOrderIn);
    }
    @Bean
    Binding bindingGetMenuIn(Queue queueGetMenuIn, TopicExchange exchange) {
        return BindingBuilder.bind(queueGetMenuIn).to(exchange).with(routingKeyGetMenuIn);
    }

    @Bean
    Binding bindingGetRestIn(Queue queueGetRestIn, TopicExchange exchange) {
        return BindingBuilder.bind(queueGetRestIn).to(exchange).with(routingKeyGetRestIn);
    }

    @Bean
    Binding bindingGetAllMenuIn(Queue queueGetAllMenuIn, TopicExchange exchange) {
        return BindingBuilder.bind(queueGetAllMenuIn).to(exchange).with(routingKeyGetAllMenuIn);
    }

    @Bean
    Binding bindingGetOrderOut(Queue queueGetOrderOut, TopicExchange exchange) {
        return BindingBuilder.bind(queueGetOrderOut).to(exchange).with(routingKeyGetOrderOut);
    }

    @Bean
    Binding bindingNewOrderOut(Queue queueNewOrderOut, TopicExchange exchange) {
        return BindingBuilder.bind(queueNewOrderOut).to(exchange).with(routingKeyNewOrderOut);
    }

    @Bean
    Binding bindingGetMenuOut(Queue queueGetMenuOut, TopicExchange exchange) {
        return BindingBuilder.bind(queueGetMenuOut).to(exchange).with(routingKeyGetMenuOut);
    }

    @Bean
    Binding bindingGetRestOut(Queue queueGetRestOut, TopicExchange exchange) {
        return BindingBuilder.bind(queueGetRestOut).to(exchange).with(routingKeyGetRestOut);
    }

    @Bean
    Binding bindingGetAllMenuOut(Queue queueGetAllMenuOut, TopicExchange exchange) {
        return BindingBuilder.bind(queueGetAllMenuOut).to(exchange).with(routingKeyGetAllMenuOut);
    }


    @Bean
    Binding bindingErr(Queue queueErr, TopicExchange exchange) {
        return BindingBuilder.bind(queueErr).to(exchange).with(routingKeyErr);
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

    public String getQueueGetOrderIn() {
        return queueGetOrderIn;
    }

    public void setQueueGetOrderIn(String queueGetOrderIn) {
        this.queueGetOrderIn = queueGetOrderIn;
    }

    public String getQueueNewOrderIn() {
        return queueNewOrderIn;
    }

    public void setQueueNewOrderIn(String queueNewOrderIn) {
        this.queueNewOrderIn = queueNewOrderIn;
    }

    public String getQueueGetMenuIn() {
        return queueGetMenuIn;
    }

    public void setQueueGetMenuIn(String queueGetMenuIn) {
        this.queueGetMenuIn = queueGetMenuIn;
    }

    public String getQueueGetRestIn() {
        return queueGetRestIn;
    }

    public void setQueueGetRestIn(String queueGetRestIn) {
        this.queueGetRestIn = queueGetRestIn;
    }

    public String getQueueGetAllMenuIn() {
        return queueGetAllMenuIn;
    }

    public void setQueueGetAllMenuIn(String queueGetAllMenuIn) {
        this.queueGetAllMenuIn = queueGetAllMenuIn;
    }


    public String getQueueErr() {
        return queueErr;
    }

    public void setQueueErr(String queueErr) {
        this.queueErr = queueErr;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKeyGetAllMenuIn() {
        return routingKeyGetAllMenuIn;
    }

    public void setRoutingKeyGetAllMenuIn(String routingKeyGetAllMenuIn) {
        this.routingKeyGetAllMenuIn = routingKeyGetAllMenuIn;
    }

    public String getRoutingKeyGetRestIn() {
        return routingKeyGetRestIn;
    }

    public void setRoutingKeyGetRestIn(String routingKeyGetRestIn) {
        this.routingKeyGetRestIn = routingKeyGetRestIn;
    }

    public String getRoutingKeyGetMenuIn() {
        return routingKeyGetMenuIn;
    }

    public void setRoutingKeyGetMenuIn(String routingKeyGetMenuIn) {
        this.routingKeyGetMenuIn = routingKeyGetMenuIn;
    }

    public String getRoutingKeyNewOrderIn() {
        return routingKeyNewOrderIn;
    }

    public void setRoutingKeyNewOrderIn(String routingKeyNewOrderIn) {
        this.routingKeyNewOrderIn = routingKeyNewOrderIn;
    }

    public String getRoutingKeyGetOrderIn() {
        return routingKeyGetOrderIn;
    }

    public void setRoutingKeyGetOrderIn(String routingKeyGetOrderIn) {
        this.routingKeyGetOrderIn = routingKeyGetOrderIn;
    }


    public String getRoutingKeyErr() {
        return routingKeyErr;
    }

    public void setRoutingKeyErr(String routingKeyErr) {
        this.routingKeyErr = routingKeyErr;
    }

    public String getQueueGetOrderOut() {
        return queueGetOrderOut;
    }

    public void setQueueGetOrderOut(String queueGetOrderOut) {
        this.queueGetOrderOut = queueGetOrderOut;
    }

    public String getQueueNewOrderOut() {
        return queueNewOrderOut;
    }

    public void setQueueNewOrderOut(String queueNewOrderOut) {
        this.queueNewOrderOut = queueNewOrderOut;
    }

    public String getQueueGetMenuOut() {
        return queueGetMenuOut;
    }

    public void setQueueGetMenuOut(String queueGetMenuOut) {
        this.queueGetMenuOut = queueGetMenuOut;
    }

    public String getQueueGetRestOut() {
        return queueGetRestOut;
    }

    public void setQueueGetRestOut(String queueGetRestOut) {
        this.queueGetRestOut = queueGetRestOut;
    }

    public String getQueueGetAllMenuOut() {
        return queueGetAllMenuOut;
    }

    public void setQueueGetAllMenuOut(String queueGetAllMenuOut) {
        this.queueGetAllMenuOut = queueGetAllMenuOut;
    }

    public String getRoutingKeyGetAllMenuOut() {
        return routingKeyGetAllMenuOut;
    }

    public void setRoutingKeyGetAllMenuOut(String routingKeyGetAllMenuOut) {
        this.routingKeyGetAllMenuOut = routingKeyGetAllMenuOut;
    }

    public String getRoutingKeyGetRestOut() {
        return routingKeyGetRestOut;
    }

    public void setRoutingKeyGetRestOut(String routingKeyGetRestOut) {
        this.routingKeyGetRestOut = routingKeyGetRestOut;
    }

    public String getRoutingKeyGetMenuOut() {
        return routingKeyGetMenuOut;
    }

    public void setRoutingKeyGetMenuOut(String routingKeyGetMenuOut) {
        this.routingKeyGetMenuOut = routingKeyGetMenuOut;
    }

    public String getRoutingKeyNewOrderOut() {
        return routingKeyNewOrderOut;
    }

    public void setRoutingKeyNewOrderOut(String routingKeyNewOrderOut) {
        this.routingKeyNewOrderOut = routingKeyNewOrderOut;
    }

    public String getRoutingKeyGetOrderOut() {
        return routingKeyGetOrderOut;
    }

    public void setRoutingKeyGetOrderOut(String routingKeyGetOrderOut) {
        this.routingKeyGetOrderOut = routingKeyGetOrderOut;
    }
}