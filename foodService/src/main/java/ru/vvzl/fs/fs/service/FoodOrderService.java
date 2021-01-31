package ru.vvzl.fs.fs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.vvzl.fs.fs.DAO.FoodServiceDAO;
import ru.vvzl.fs.fs.config.Config;
import ru.vvzl.fs.fs.config.FeignClients;
import ru.vvzl.fs.fs.config.ScopeRefreshedListener;
import ru.vvzl.fs.rs.api.RestaurantApi;
import ru.vvzl.fs.rs.model.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FoodOrderService {
    private final Logger logger = LoggerFactory.getLogger(FoodOrderService.class);

    @Autowired
    private FeignClients feignClients;

    @Autowired
    private ScopeRefreshedListener refreshedListener;
    @Autowired
    private Config config;

    @Autowired
    private FoodServiceDAO foodServiceDAO;

    @PostConstruct
    private void init() {
        refreshedListener.refresh();
    }

    public List<Restaurant> getRestaurants(){

        List<Restaurant> restaurants = new ArrayList<>();
        refreshedListener.getList().stream().forEach(name -> {
            Restaurant restaurant = new Restaurant();
            restaurant.setName(name);
            restaurants.add(restaurant);
        });
        return restaurants;
    }

    public List<List<AssetResponse>> getAllMenu(){
        List<List<AssetResponse>> menu = new ArrayList<>();
        refreshedListener.getList().forEach(name->{
            menu.add(getMenu(name));
        });
        return menu;
    }

    public List<AssetResponse> getMenu(String restaurant) {
        if (refreshedListener.getList().contains(restaurant)) {
            RestaurantApi client = feignClients.getClient(restaurant);
            ResponseEntity<List<AssetResponse>> menu = client.getMenu();
            if (menu.getStatusCode() == HttpStatus.OK && menu.hasBody()) {
                return menu.getBody();
            } else {
                logger.error("------------------------->>>>>>>>>>>>>>>>>Failed to get menu with status !");
                throw new ResponseStatusException(menu.getStatusCode());
            }
        }else {
            logger.error("------------------------->>>>>>>>>>>>>>>>>Restaurant is not correspond");

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Restaurant is not correspond");
        }


    }
    public AddOrderResponse newOrder(String restaurant, List<Order> order) {
        if (refreshedListener.getList().contains(restaurant)) {
            RestaurantApi client = feignClients.getClient(restaurant);
            ResponseEntity<AddOrderResponse> newOrder = client.addOrder(order);
            if (newOrder.getStatusCode() == HttpStatus.OK && newOrder.hasBody()) {
                String orderId = restaurant + "-" + Objects.requireNonNull(newOrder.getBody()).getOrderid();
                try {
                    foodServiceDAO.addOrder(orderId);

                } catch (Exception e) {
                    logger.error("------------------------->>>>>>>>>>>>>>>>>Not Access to DATABASE!");
                    throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Not Access to DATABASE");
                }
                AddOrderResponse addOrderResponse = new AddOrderResponse();
                addOrderResponse.setOrderid(orderId);
                return addOrderResponse;
            } else {
                logger.error("------------------------->>>>>>>>>>>>>>>>>Failed to get newOrder with status !");
                throw new ResponseStatusException(newOrder.getStatusCode());
            }

        } else {
            logger.error("------------------------->>>>>>>>>>>>>>>>>Restaurant is not correspond");

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant is not correspond");
        }
    }
    public OrderResponse getOrder(String orderId){
        if(foodServiceDAO.orderInBase(orderId) != null){
            String [] parts = orderId.split("-");
            if (refreshedListener.getList().contains(parts[0])) {
                RestaurantApi client = feignClients.getClient(parts[0]);
                ResponseEntity<OrderResponse> order = client.getOrder(Integer.parseInt(parts[1]));
                if (order.getStatusCode() == HttpStatus.OK && order.hasBody()) {
                    return order.getBody();
                } else {
                    logger.error("------------------------->>>>>>>>>>>>>>>>>Failed to get order with status !");
                    throw new ResponseStatusException(order.getStatusCode());
                }
            }else {
                    logger.error("------------------------->>>>>>>>>>>>>>>>>Restaurant is not correspond");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant is not correspond");
                }
        } else {
            logger.error("------------------------->>>>>>>>>>>>>>>>>Order not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }


    }



}
