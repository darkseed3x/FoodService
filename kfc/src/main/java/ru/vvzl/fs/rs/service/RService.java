package ru.vvzl.fs.rs.service;

import ru.vvzl.fs.rs.model.*;

import java.util.List;

public interface RService {
     AssetResponse getAsset( Integer id);
     List<AssetResponse> getMenu();
    AddAssetResponse addAsset(Asset asset);
    void deleteAsset(Integer id);
    AddOrderResponse addOrder(List<Order> order );
    OrderResponse getOrder( Integer id);
}
