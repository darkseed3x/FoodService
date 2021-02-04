package ru.vvzl.fs.fs.model;

import ru.vvzl.fs.rs.model.Order;

import java.util.List;

public class NewOrderReqRabbit {
    private List<Order> orders;
    private String restaurant;

    public List<Order> getOrders() {
        return orders;
    }

    public String getRestaurant() {
        return restaurant;
    }

}
