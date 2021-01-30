package ru.mpk.client.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParameterDto {

    @JsonProperty
    private String name;

    @JsonProperty
    private String value;

    @JsonFormat(pattern = "dd:MM:yyyy HH:mm:ss")
    @JsonProperty
    private LocalDateTime dateTime;
}
