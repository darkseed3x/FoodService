package ru.vvzl.fs.fs.DAO.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.vvzl.fs.fs.DAO.FoodServiceDAO;
@Repository
public class FoodServiceDAOimpl implements FoodServiceDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addOrder(String orderId) {
        jdbcTemplate.update("insert into orders (common_order_id) values (?)", orderId);
    }
}
