package ru.vvzl.fs.fs.messaging;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vvzl.fs.fs.config.RabbitConfig;
import ru.vvzl.fs.rs.model.AddOrderResponse;
import ru.vvzl.fs.rs.model.AssetResponse;
import ru.vvzl.fs.rs.model.OrderResponse;
import ru.vvzl.fs.rs.model.Restaurant;

import java.util.List;

@Service
public class MessageSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitConfig rabbitConfig;



    public void sendMessageOrderResponse(OrderResponse response) {

            this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), rabbitConfig.getRoutingKeyGetOrderOut(), response);


    }
    public void sendMessageNewOrderResponse(AddOrderResponse response) {

        this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), rabbitConfig.getRoutingKeyNewOrderOut(), response);


    }

    public void sendMessageGetRestaurantsResponse(List<Restaurant> response) {

        this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), rabbitConfig.getRoutingKeyGetRestOut(), response);


    }


    public void sendMessageGetMenuResponse(List<AssetResponse> response) {

        this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), rabbitConfig.getRoutingKeyGetMenuOut(), response);


    }
    public void sendMessageGetAllMenuResponse(List<List<AssetResponse>> response) {

        this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), rabbitConfig.getRoutingKeyGetAllMenuOut(), response);


    }
    public void sendErrorMessage(String response) {

        this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), rabbitConfig.getRoutingKeyErr(), response);


    }
}