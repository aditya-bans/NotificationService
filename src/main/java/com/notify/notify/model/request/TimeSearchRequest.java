package com.notify.notify.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeSearchRequest {
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    private  LocalDateTime endTime;


}
