package ru.mpk.client.service.mpkaddress;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "mpkaddress", url = "http://${server.host}:${server.port}/mpkaddresses")
public interface MpkAddressClient {

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    MpkAddressDto create(@RequestBody MpkAddressCreationParameters creationParameters);
}
