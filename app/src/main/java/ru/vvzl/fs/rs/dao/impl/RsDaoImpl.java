package ru.vvzl.fs.rs.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.vvzl.fs.rs.dao.AssetResponseMapper;
import ru.vvzl.fs.rs.dao.OrderDTOMapper;
import ru.vvzl.fs.rs.dao.OrderMapper;
import ru.vvzl.fs.rs.dao.RsDAO;
import ru.vvzl.fs.rs.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RsDaoImpl implements RsDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public AssetResponse getAsset(Integer id) {
        return jdbcTemplate.queryForObject("select * from assets where asset_id = ?", new AssetResponseMapper(), id);
    }

    @Override
    public List<AssetResponse> getMenu() {

        return jdbcTemplate.query("select * from assets ", new AssetResponseMapper());
    }

    @Override
    public AddAssetResponse addAsset(Asset asset) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into assets (name, description, price) values (?, ?, ?)", new String[]{"asset_id"});
            ps.setString(1, asset.getName());
            ps.setString(2, asset.getDescription());
            ps.setBigDecimal(3, asset.getPrice());
            return ps;
        }, keyHolder);
        AddAssetResponse addAssetResponse = new AddAssetResponse();
        addAssetResponse.setId((Integer) keyHolder.getKey());

        return addAssetResponse;
    }

    @Override
    public void deleteAsset(Integer id) {
        jdbcTemplate.update("delete from assets where asset_id=?", Long.valueOf(id));

    }

    @Override
    public AddOrderResponse addOrder(List<Order> order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into orders (created) values (?)", new String[]{"order_id"});
            ps.setTimestamp(1, Timestamp.from(OffsetDateTime.now().toInstant()));
            return ps;
        }, keyHolder);

        jdbcTemplate.batchUpdate(
                "insert into order_items (asset_id, order_id, amount) values(?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, order.get(i).getAssestId());
                        ps.setInt(2, (Integer) keyHolder.getKey());
                        ps.setInt(3, order.get(i).getCount());
                    }

                    public int getBatchSize() {
                        return order.size();
                    }
                });
        AddOrderResponse addOrderResponse = new AddOrderResponse();
        addOrderResponse.setOrderid(String.valueOf( keyHolder.getKey()));
        return addOrderResponse;
    }

    @Override
    public OrderResponse getOrder(Integer id) {
        final BigDecimal[] price = {new BigDecimal("0")};
        OrderDTO orderDTO = jdbcTemplate.queryForObject("select * from orders where order_id = ?", new OrderDTOMapper(), id);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(orderDTO.getOrderId());
        orderResponse.setCreated(orderDTO.getCreated());

        List<Order> listOrder = jdbcTemplate.query("select * from order_items where order_id = ?", new OrderMapper(), id);

        List<OrderResponseOrder> list = listOrder.stream().map(order -> {
            OrderResponseOrder orderResponseOrder = new OrderResponseOrder();
            orderResponseOrder.setCount(order.getCount());
            AssetResponse asset = getAsset(order.getAssestId());
            price[0] = price[0].add(asset.getPrice());
            orderResponseOrder.setAsset(asset);
            return orderResponseOrder;
        }).collect(Collectors.toList());


        orderResponse.setPrice(price[0].setScale(2, RoundingMode.FLOOR));
        orderResponse.setOrder(list);


        return orderResponse;
    }


}
