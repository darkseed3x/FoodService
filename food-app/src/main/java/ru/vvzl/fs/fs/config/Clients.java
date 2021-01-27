package ru.vvzl.fs.fs.config;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import ru.vvzl.fs.fs.obs.Observer;
import ru.vvzl.fs.rs.api.RestaurantApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@Import(FeignClientsConfiguration.class)
public class Clients implements Observer {
    private final Config config;
    private final List<RestaurantApi> clients = Collections.synchronizedList(new ArrayList<>());
    private final Decoder decoder;
    private final Encoder encoder;
    private final Client client;
    private final Contract contract;
    private final Map<String, RestaurantApi> list;

    public Clients(Config config, Decoder decoder, Encoder encoder, Client client, Contract contract, Map<String, RestaurantApi> list) {
        this.config = config;
        this.decoder = decoder;
        this.encoder = encoder;
        this.client = client;
        this.contract = contract;
        this.list = list;
        this.onChange();
    }

    @Override
    public void onChange() {
        synchronized (clients) {
            clients.clear();
            config.getNames().forEach(name -> {
                RestaurantApi api = createFeign(name);
                list.put(name, api);
                clients.add(api);
            });
        }
    }
    public List<RestaurantApi> getClients() {
        return List.copyOf(clients);
    }

    public Map<String, RestaurantApi> getMapResto(){
        return list;
    }

    private RestaurantApi createFeign(String name) {
        return Feign.builder()
                .client(client)
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .target(RestaurantApi.class, String.format("http://%s", name));
    }

}
