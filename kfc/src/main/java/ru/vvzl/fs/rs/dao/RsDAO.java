package ru.vvzl.fs.rs.dao;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.KeyHolder;
import ru.vvzl.fs.rs.model.*;

import javax.validation.Valid;
import java.util.List;

public interface RsDAO {
     AssetResponse getAsset(Integer id);
     List<AssetResponse> getMenu();
     KeyHolder createAsset(Asset asset);
     void deleteAsset(Integer id);
     void createOrderItems(List<Order> order, KeyHolder keyHolder);
     KeyHolder createOrder();
     OrderDTO getOrderFromBase(Integer id);
     List<Order> getOrderItems(Integer id);
}
