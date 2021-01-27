package ru.vvzl.fs.fs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vvzl.fs.fs.service.FoodOrderService;
import ru.vvzl.fs.rs.api.FoodOrderServiceApi;
import ru.vvzl.fs.rs.model.AddOrderResponse;
import ru.vvzl.fs.rs.model.AssetResponse;
import ru.vvzl.fs.rs.model.Order;
import ru.vvzl.fs.rs.model.OrderResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FoodServiceController implements FoodOrderServiceApi{
    private final FoodOrderService service;

    public FoodServiceController(FoodOrderService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<OrderResponse> getCommonOrder( @Valid String orderId) {
        return ResponseEntity.ok(service.getOrder(orderId));
    }

    @Override
    public ResponseEntity<List<AssetResponse>> menu(@Valid String restaurant) {
        return ResponseEntity.ok(service.getMenu(restaurant));
    }

    @Override
    public ResponseEntity<AddOrderResponse> newOrder(@Valid String restaurant, @Valid List<Order> order) {
        return ResponseEntity.ok(service.newOrder(restaurant, order));
    }


}
