package ru.vvzl.fs.rs.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.vvzl.fs.rs.model.OrderDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;

public class OrderDTOMapper implements RowMapper<OrderDTO> {

    @Override
    public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(rs.getInt("order_id"));
        orderDTO.setCreated(rs.getTimestamp("created").toLocalDateTime().atOffset(ZoneOffset.UTC));
        return orderDTO;
    }
}
