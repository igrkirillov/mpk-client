package ru.mpk.client.service.fiasaddress;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "fias", url = "http://${server.host}:${server.port}/fiasaddresses")
public interface FiasAddressClient {

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    List<FiasAddressDto> getList(@RequestParam("query") String query);
}
