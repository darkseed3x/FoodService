package ru.vvzl.fs.fs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ScopeRefreshedListener {
    private final List<String> list = new ArrayList<>();

    @Autowired
    private Config config;

    @Autowired
    private FeignClients feignClients;

    public List<String> getList() {
        return list;
    }

    @EventListener(RefreshScopeRefreshedEvent.class)
    public void onRefresh(RefreshScopeRefreshedEvent event) {
        refresh();
    }

    public void refresh() {
        list.clear();
        for (String s : config.getNames()) {
            try {
                feignClients.getClient(s).getMenu();
                list.add(s);
            } catch (Exception e) {
            }
        }
    }
}