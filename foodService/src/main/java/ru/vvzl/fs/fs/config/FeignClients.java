package ru.vvzl.fs.fs.config;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import ru.vvzl.fs.rs.api.RestaurantApi;

@Component
@Import(FeignClientsConfiguration.class)
public class FeignClients {
    private final Decoder decoder;
    private final Encoder encoder;
    private final Client client;
    private final Contract contract;

    public FeignClients(Decoder decoder, Encoder encoder, Client client, Contract contract) {
        this.decoder = decoder;
        this.encoder = encoder;
        this.client = client;
        this.contract = contract;
    }
    public RestaurantApi getClient(String name) {
        return createFeign(name);
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
