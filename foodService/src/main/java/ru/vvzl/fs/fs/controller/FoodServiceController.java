package ru.vvzl.fs.fs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.vvzl.fs.fs.service.FoodOrderService;
import ru.vvzl.fs.rs.api.FoodOrderServiceApi;
import ru.vvzl.fs.rs.model.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FoodServiceController implements FoodOrderServiceApi{
    @Autowired
    private  FoodOrderService service;


    @Override
    public ResponseEntity<List<List<AssetResponse>>> allMenu() {
        return ResponseEntity.ok(service.getAllMenu());
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

    @Override
    public ResponseEntity<List<Restaurant>> restoList() {
        return ResponseEntity.ok(service.getRestaurants());
    }


}
