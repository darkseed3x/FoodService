package ru.vvzl.fs.fs.DAO.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;
import ru.vvzl.fs.fs.DAO.FoodServiceDAO;

import javax.annotation.PostConstruct;

@Repository
public class FoodServiceDAOimpl implements FoodServiceDAO {

    @Value("${spring.flyway.placeholders.foodServiceName}")
    private String tableName;
    @Value("${foodService.db.schema}")
    private String schemaName;
    private String insertOrder;
    private String selectOrder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init(){
        insertOrder = String.format("insert into %s.%s_ORDERS (common_order_id) values (?)",schemaName, tableName);
        selectOrder = String.format("select id  from %s.%s_ORDERS where common_order_id=?",schemaName, tableName);

    }

    @Override
    @Retryable(value = RuntimeException.class,
            maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public void addOrder(String orderId) {
        jdbcTemplate.update(insertOrder, orderId);
    }

    @Override
    @Retryable(value = RuntimeException.class,
            maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public String orderInBase(String orderId) {
        try {
            return jdbcTemplate.queryForObject(selectOrder, String.class,orderId);

        }catch (EmptyResultDataAccessException e){
            return null;
        }


    }
}
