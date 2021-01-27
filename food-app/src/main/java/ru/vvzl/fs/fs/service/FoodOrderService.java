package ru.vvzl.fs.fs.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.vvzl.fs.fs.DAO.FoodServiceDAO;
import ru.vvzl.fs.fs.config.Clients;
import ru.vvzl.fs.rs.api.RestaurantApi;
import ru.vvzl.fs.rs.model.AddOrderResponse;
import ru.vvzl.fs.rs.model.AssetResponse;
import ru.vvzl.fs.rs.model.Order;
import ru.vvzl.fs.rs.model.OrderResponse;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FoodOrderService {

    private final Clients clients;
    private final Map<String, RestaurantApi> list;
    private FoodServiceDAO foodServiceDAO;

    public FoodOrderService(Clients clients, FoodServiceDAO foodServiceDAO) {
        this.clients = clients;
        list = clients.getMapResto();
        this.foodServiceDAO = foodServiceDAO;
    }

    public List<AssetResponse> getMenu(String restaurant) {
        RestaurantApi client = list.get(restaurant);
        ResponseEntity<List<AssetResponse>> menu = client.getMenu();
        if (menu.getStatusCode() == HttpStatus.OK && menu.hasBody()) {
            return menu.getBody();
        } else {
            return new ArrayList<>();

        }
    }
    public AddOrderResponse newOrder(String restaurant, List<Order> order){
        RestaurantApi client = list.get(restaurant);
         String id = restaurant + "-" + client.addOrder(order).getBody().getOrderid();
         foodServiceDAO.addOrder(id);
        AddOrderResponse addOrderResponse = new AddOrderResponse();
        addOrderResponse.setOrderid(id);
        return addOrderResponse;
    }
    public OrderResponse getOrder(String orderId){
        String [] parts = orderId.split("-");
        RestaurantApi client = list.get(parts[0]);
        return client.getOrder(Integer.parseInt(parts[1])).getBody();
    }
}
