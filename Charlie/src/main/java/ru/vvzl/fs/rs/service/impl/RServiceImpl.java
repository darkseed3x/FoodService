package ru.vvzl.fs.rs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.vvzl.fs.rs.dao.RsDAO;
import ru.vvzl.fs.rs.model.*;
import ru.vvzl.fs.rs.service.RService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RServiceImpl implements RService {

    @Autowired
    private RsDAO rsDAO;


    @Override
    public AssetResponse getAsset(Integer id) {

        return rsDAO.getAsset(id);
    }

    @Override
    public List<AssetResponse> getMenu() {
        return rsDAO.getMenu();
    }

    @Override
    public AddAssetResponse addAsset(Asset asset) {
        KeyHolder keyHolder = rsDAO.createAsset(asset);
        AddAssetResponse addAssetResponse = new AddAssetResponse();
        addAssetResponse.setId((Integer) keyHolder.getKey());

        return addAssetResponse;
    }

    @Override
    public void deleteAsset(Integer id) {
        try {
            rsDAO.deleteAsset(id);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"");
        }
    }

    @Transactional
    @Override
    public AddOrderResponse addOrder(List<Order> order ) {
        KeyHolder keyHolder = rsDAO.createOrder();

        rsDAO.createOrderItems(order, keyHolder);
        AddOrderResponse addOrderResponse = new AddOrderResponse();
        addOrderResponse.setOrderid(String.valueOf( keyHolder.getKey()));
        return addOrderResponse;
    }

    @Override
    public OrderResponse getOrder(Integer id) {
        final BigDecimal[] price = {new BigDecimal("0")};
        OrderDTO orderDTO = rsDAO.getOrderFromBase(id);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(orderDTO.getOrderId());
        orderResponse.setCreated(orderDTO.getCreated());

        List<Order> listOrder = rsDAO.getOrderItems(id);

        List<OrderResponseOrder> list = listOrder.stream().map(order -> {
            OrderResponseOrder orderResponseOrder = new OrderResponseOrder();
            orderResponseOrder.setCount(order.getCount());
            AssetResponse asset = getAsset(order.getAssestId());
            price[0] = price[0].add(asset.getPrice().multiply(new BigDecimal(order.getCount())));
            orderResponseOrder.setAsset(asset);
            return orderResponseOrder;
        }).collect(Collectors.toList());


        orderResponse.setPrice(price[0].setScale(2, RoundingMode.FLOOR));
        orderResponse.setOrder(list);

        return orderResponse;
    }
}
