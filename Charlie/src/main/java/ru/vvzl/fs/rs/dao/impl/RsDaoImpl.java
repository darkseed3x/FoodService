package ru.vvzl.fs.rs.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.vvzl.fs.rs.dao.AssetResponseMapper;
import ru.vvzl.fs.rs.dao.OrderDTOMapper;
import ru.vvzl.fs.rs.dao.OrderMapper;
import ru.vvzl.fs.rs.dao.RsDAO;
import ru.vvzl.fs.rs.model.Asset;
import ru.vvzl.fs.rs.model.AssetResponse;
import ru.vvzl.fs.rs.model.Order;
import ru.vvzl.fs.rs.model.OrderDTO;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class RsDaoImpl implements RsDAO {

    @Value("${spring.flyway.placeholders.restaName}")
    private String tableName;
    @Value("${restaurant.db.schema}")
    private String schemaName;
    private String queryAssetObj;
    private String queryMenu;
    private String addAssetStatement;
    private String deleteAsset;
    private String addOrderInsert;
    private String addOrderBatch;
    private String getOrderObj;
    private String getOrderQuery;

    @PostConstruct
    private void init() {
        queryAssetObj = String.format("select * from %s.%s_ASSETS where asset_id = ?", schemaName, tableName);
        queryMenu = String.format("select * from %s.%s_ASSETS", schemaName, tableName);
        addAssetStatement = String.format("insert into %s.%s_ASSETS (name, description, price) values (?, ?, ?)", schemaName, tableName);
        deleteAsset = String.format("delete from %s.%s_ASSETS where asset_id=?", schemaName, tableName);
        addOrderInsert = String.format("insert into %s.%s_ORDERS (created) values (?)", schemaName, tableName);
        addOrderBatch = String.format("insert into %s.%s_ORDER_ITEMS (asset_id, order_id, amount) values(?, ?, ?)", schemaName, tableName);
        getOrderObj = String.format("select * from %s.%s_ORDERS where order_id = ?", schemaName, tableName);
        getOrderQuery = String.format("select * from %s.%s_ORDER_ITEMS  where order_id = ?", schemaName, tableName);

    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public AssetResponse getAsset(Integer id) {
        return jdbcTemplate.queryForObject(queryAssetObj, new AssetResponseMapper(), id);
    }

    @Override
    public List<AssetResponse> getMenu() {

        return jdbcTemplate.query(queryMenu, new AssetResponseMapper());
    }

    @Override
    public KeyHolder createAsset(Asset asset) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(addAssetStatement, new String[]{"asset_id"});
            ps.setString(1, asset.getName());
            ps.setString(2, asset.getDescription());
            ps.setBigDecimal(3, asset.getPrice());
            return ps;
        }, keyHolder);
        return keyHolder;
    }

    @Override
    public void deleteAsset(Integer id) {
        jdbcTemplate.update(deleteAsset, Long.valueOf(id));

    }


    @Override
    public void createOrderItems(List<Order> order, KeyHolder keyHolder) {
        jdbcTemplate.batchUpdate(addOrderBatch,
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
    }
    @Override
    public KeyHolder createOrder() {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(addOrderInsert, new String[]{"order_id"});
            ps.setTimestamp(1, Timestamp.from(OffsetDateTime.now().toInstant()));
            return ps;
        }, keyHolder);
        return keyHolder;
    }


    @Override
    public List<Order> getOrderItems(Integer id) {
        List<Order> listOrder = jdbcTemplate.query(getOrderQuery, new OrderMapper(), id);
        return listOrder;
    }
    @Override
    public OrderDTO getOrderFromBase(Integer id) {
        OrderDTO orderDTO = jdbcTemplate.queryForObject(getOrderObj, new OrderDTOMapper(), id);
        return orderDTO;
    }


}
