package ru.vvzl.fs.fs.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(MessageListener.class);
    @Autowired
    private FoodOrderService service;
    @Autowired
    private MessageSender messageSender;

    @RabbitListener(queues = "${service.rabbit.queueGetOrderIn:testIn}")
    public void getOrder(OrderReqRabbit req) {

        try {
            messageSender.sendMessageOrderResponse(service.getOrder(req.getOrderId()));
        }catch (Exception e){
            messageSender.sendErrorMessage(e.getMessage());
            logger.error(e.getMessage());
        }

    }

    @RabbitListener(queues = "${service.rabbit.queueNewOrderIn:testIn}")
    public void newOrder(NewOrderReqRabbit req) {

        try {
            messageSender.sendMessageNewOrderResponse(service.newOrder(req.getRestaurant(), req.getOrders()));
        }catch (Exception e){
            messageSender.sendErrorMessage(e.getMessage());
            logger.error(e.getMessage());
        }

    }
    @RabbitListener(queues = "${service.rabbit.queueGetMenuIn:testIn}")
    public void getMenu(MenuReqRabbit req) {

        try {
            messageSender.sendMessageGetMenuResponse(service.getMenu(req.getRestaurant()));
        }catch (Exception e){
            messageSender.sendErrorMessage(e.getMessage());
            logger.error(e.getMessage());
        }
    }
    @RabbitListener(queues = "${service.rabbit.queueGetRestIn:testIn}")
    public void getRestaurants(BlankReqRabbit req) {

        try {
            messageSender.sendMessageGetRestaurantsResponse(service.getRestaurants());
        }catch (Exception e){
            messageSender.sendErrorMessage(e.getMessage());
            logger.error(e.getMessage());
        }
    }
    @RabbitListener(queues = "${service.rabbit.queueGetAllMenuIn:testIn}")
    public void getAllMenu(BlankReqRabbit req) {

        try {
            messageSender.sendMessageGetAllMenuResponse(service.getAllMenu());
        }catch (Exception e){
            messageSender.sendErrorMessage(e.getMessage());
            logger.error(e.getMessage());
        }
    }
}
