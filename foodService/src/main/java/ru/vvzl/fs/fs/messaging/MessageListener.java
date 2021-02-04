package ru.vvzl.fs.fs.messaging;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vvzl.fs.fs.model.BlankReqRabbit;
import ru.vvzl.fs.fs.model.MenuReqRabbit;
import ru.vvzl.fs.fs.model.NewOrderReqRabbit;
import ru.vvzl.fs.fs.model.OrderReqRabbit;
import ru.vvzl.fs.fs.service.FoodOrderService;

@Component
public class MessageListener {
    @Autowired
    private FoodOrderService service;
    @Autowired
    private MessageSender messageSender;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${service.rabbit.queueGetOrderIn:testIn}", durable = "true"),
            exchange = @Exchange(value = "${service.rabbit.exchange:testExcIn}", ignoreDeclarationExceptions = "true"),
            key = "${service.rabbit.routingKeyGetOrderIn:testRkGoIn}")
    )
    public void getOrder(OrderReqRabbit req) {

        try {
            messageSender.sendMessageOrderResponse(service.getOrder(req.getOrderId()));
        }catch (Exception e){
            messageSender.sendErrorMessage(e.getMessage());
        }

    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${service.rabbit.queueNewOrderIn:testIn}", durable = "true"),
            exchange = @Exchange(value = "${service.rabbit.exchange:testExcIn}", ignoreDeclarationExceptions = "true"),
            key = "${service.rabbit.routingKeyNewOrderIn:testRkGoIn}")
    )
    public void newOrder(NewOrderReqRabbit req) {

        try {
            messageSender.sendMessageNewOrderResponse(service.newOrder(req.getRestaurant(), req.getOrders()));
        }catch (Exception e){
            messageSender.sendErrorMessage(e.getMessage());
        }

    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${service.rabbit.queueGetMenuIn:testIn}", durable = "true"),
            exchange = @Exchange(value = "${service.rabbit.exchange:testExcIn}", ignoreDeclarationExceptions = "true"),
            key = "${service.rabbit.routingKeyGetMenuIn:testRkGoIn}")
    )
    public void getMenu(MenuReqRabbit req) {

        try {

        messageSender.sendMessageGetMenuResponse(service.getMenu(req.getRestaurant()));
        }catch (Exception e){
        messageSender.sendErrorMessage(e.getMessage());
        }
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${service.rabbit.queueGetRestIn:testIn}", durable = "true"),
            exchange = @Exchange(value = "${service.rabbit.exchange:testExcIn}", ignoreDeclarationExceptions = "true"),
            key = "${service.rabbit.routingKeyGetRestIn:testRkGoIn}")
    )
    public void getRestaurants(BlankReqRabbit req) {

        try {

        messageSender.sendMessageGetRestaurantsResponse(service.getRestaurants());
        }catch (Exception e){
        messageSender.sendErrorMessage(e.getMessage());
        }
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${service.rabbit.queueGetAllMenuIn:testIn}", durable = "true"),
            exchange = @Exchange(value = "${service.rabbit.exchange:testExcIn}", ignoreDeclarationExceptions = "true"),
            key = "${service.rabbit.routingKeyGetAllMenuIn:testRkGoIn}")
    )
    public void getAllMenu(BlankReqRabbit req) {

        try {

        messageSender.sendMessageGetAllMenuResponse(service.getAllMenu());
        }catch (Exception e){
        messageSender.sendErrorMessage(e.getMessage());
        }
    }
}
