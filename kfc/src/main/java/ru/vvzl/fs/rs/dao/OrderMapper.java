package ru.vvzl.fs.rs.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.vvzl.fs.rs.model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setCount(rs.getInt("amount"));
        order.setAssestId(rs.getInt("asset_id"));
        return order;
    }
}
