package ru.vvzl.fs.rs.dao;

import org.springframework.http.ResponseEntity;
import ru.vvzl.fs.rs.model.*;

import javax.validation.Valid;
import java.util.List;

public interface RsDAO {
     AssetResponse getAsset(Integer id);
     List<AssetResponse> getMenu();
     AddAssetResponse addAsset(Asset asset);
     void deleteAsset(Integer id);
     AddOrderResponse addOrder(List<Order> order );
     OrderResponse getOrder( Integer id);
}
