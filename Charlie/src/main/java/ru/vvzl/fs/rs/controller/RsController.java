package ru.vvzl.fs.rs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import ru.vvzl.fs.rs.api.RestaurantApi;
import ru.vvzl.fs.rs.model.*;
import ru.vvzl.fs.rs.service.RService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class RsController implements RestaurantApi {

    @Autowired
    private RService rService;



    @Override
    public ResponseEntity<AddAssetResponse> addAsset(@Valid Asset asset) {
        return ResponseEntity.ok(rService.addAsset(asset));
    }

    @Override
    public ResponseEntity<AddOrderResponse> addOrder(@Valid List<Order> order) {
        return ResponseEntity.ok(rService.addOrder(order));
    }


    @Override
    public ResponseEntity<Void> deleteAsset(@Valid Integer id) {
        rService.deleteAsset(id);
        return new ResponseEntity<Void>( HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AssetResponse> getAsset(@Valid Integer id) {
        return ResponseEntity.ok(rService.getAsset(id));
    }

    @Override
    public ResponseEntity<List<AssetResponse>> getMenu() {
        return ResponseEntity.ok(rService.getMenu());
    }


    @Override
    public ResponseEntity<OrderResponse> getOrder(@Valid Integer id) {
        return ResponseEntity.ok(rService.getOrder(id)) ;
    }
}
