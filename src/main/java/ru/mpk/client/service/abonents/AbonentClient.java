package ru.mpk.client.service.abonents;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mpk.client.service.mpkaddress.MpkAddressCreationParameters;
import ru.mpk.client.service.mpkaddress.MpkAddressDto;

import java.util.List;

@FeignClient(value = "abonents", url = "http://${server.host}:${server.port}/abonents")
public interface AbonentClient {

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    AbonentDto create(@RequestBody AbonentCreationParameters creationParameters);

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    List<AbonentDto> getList();
}
