package ru.mpk.client.service.mpkaddress;

import lombok.Data;

@Data
public class MpkAddressCreationParameters {
    private String fullName;
    private String fiasUid;
    private String zip;
}
