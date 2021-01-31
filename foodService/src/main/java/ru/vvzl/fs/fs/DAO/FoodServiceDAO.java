package ru.vvzl.fs.fs.DAO;

public interface FoodServiceDAO {
    void addOrder(String orderId);
    String orderInBase(String orderId);
}
