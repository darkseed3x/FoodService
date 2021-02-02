package ru.vvzl.fs.fs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import ru.vvzl.fs.rs.api.RestaurantApi;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ScopeRefreshedListener {
    private final Map<String,RestaurantApi> list = new HashMap<>();

    @Autowired
    private Config config;

    @Autowired
    private FeignClients feignClients;

    public Map<String,RestaurantApi> getMap() {
        return list;
    }

    @EventListener(RefreshScopeRefreshedEvent.class)
    public void onRefresh(RefreshScopeRefreshedEvent event) {
        refresh();
    }

    public void refresh() {
        list.clear();
        for (String s : config.getNames()) {
            list.put(s, feignClients.getClient(s));
        }
    }
}