package ru.vvzl.fs.rs.service.impl;

import org.springframework.stereotype.Service;
import ru.vvzl.fs.rs.dao.RsDAO;
import ru.vvzl.fs.rs.model.*;
import ru.vvzl.fs.rs.service.RService;

import java.util.List;

@Service
public class RServiceImpl implements RService {
    private RsDAO rsDAO;

    public RServiceImpl(RsDAO rsDAO) {
        this.rsDAO = rsDAO;
    }

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
        return rsDAO.addAsset(asset);
    }

    @Override
    public void deleteAsset(Integer id) {
        rsDAO.deleteAsset(id);
    }

    @Override
    public AddOrderResponse addOrder(List<Order> order ) {
        return rsDAO.addOrder(order);
    }

    @Override
    public OrderResponse getOrder(Integer id) {
        return rsDAO.getOrder(id);
    }
}
