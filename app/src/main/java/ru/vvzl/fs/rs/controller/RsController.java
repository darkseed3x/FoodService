package ru.vvzl.fs.rs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import ru.vvzl.fs.rs.api.RestaurantApi;
import ru.vvzl.fs.rs.model.Asset;
import ru.vvzl.fs.rs.service.RService;


import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class RsController implements RestaurantApi {
    private  final RService rService;

    public RsController(RService rService) {
        this.rService = rService;
    }


    @Override
    public ResponseEntity<Void> addAsset(@Valid Asset asset) {
        return null;
    }

    @Override
    public ResponseEntity<Asset> getAsset(@Valid Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Asset>> getMenu() {
        return null;
    }
}
