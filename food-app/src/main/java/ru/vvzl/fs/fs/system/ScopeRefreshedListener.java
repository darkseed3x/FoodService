package ru.vvzl.fs.fs.system;

import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.vvzl.fs.fs.config.Config;
import ru.vvzl.fs.fs.obs.Observable;
import ru.vvzl.fs.fs.obs.Observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Component
public class ScopeRefreshedListener implements ApplicationListener<RefreshScopeRefreshedEvent>, Observable {
    private final Config config;
    private final List<Observer> listeners = Collections.synchronizedList(new ArrayList<>());

    public ScopeRefreshedListener(Config config) {
        this.config = config;
    }

    @Override
    public void onApplicationEvent(RefreshScopeRefreshedEvent event) {
        listeners.forEach(observer -> {
            try {
                observer.onChange();
            } catch (Exception e) {
               e.printStackTrace();
            }
        });
    }

    @Override
    public void listen(Observer observer) {
        listeners.add(observer);
    }
}
