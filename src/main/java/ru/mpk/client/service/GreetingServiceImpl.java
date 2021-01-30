package ru.mpk.client.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;

import java.net.URI;

@Service
public class GreetingServiceImpl implements GreetingService {

    private final UriBuilderFactory uriBuilderFactory;
    private final RestTemplate rest;

    public GreetingServiceImpl(ServerAddress serverAddress) {
        uriBuilderFactory = new DefaultUriBuilderFactory(serverAddress.getServerUrl() + "api/");
        rest = new RestTemplate();
    }

    @Override
    public String greeting(String name) {
        URI url = uriBuilderFactory.builder()
                .path("/greeting")
                .queryParam("name", name).build();
        return rest.getForObject(url, String.class);
    }
}