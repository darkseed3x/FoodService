package ru.vvzl.fs.fs.DAO;

import ru.vvzl.fs.rs.model.AddOrderResponse;
import ru.vvzl.fs.rs.model.Order;

import java.util.List;

public interface FoodServiceDAO {
    void addOrder(String orderId);
}
