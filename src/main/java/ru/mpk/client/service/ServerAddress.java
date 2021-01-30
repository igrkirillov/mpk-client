package ru.mpk.client.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ServerAddress {

    @Value("${serverHost:localhost}")
    private String host;

    @Value("${serverPort:8096}")
    private int port;

    public String getServerUrl() {
        return "http://" + host + ":" + port + "/";
    }
}
