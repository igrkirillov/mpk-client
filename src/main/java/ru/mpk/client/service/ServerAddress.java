package ru.mpk.client.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ServerAddress {

    @Value("${server.host}")
    private String host;

    @Value("${server.port}")
    private int port;

    public String getServerUrl() {
        return "http://" + host + ":" + port + "/";
    }
}
