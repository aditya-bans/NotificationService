package com.notify.notify.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SendSMSRequest {

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("message")
    private String message;

}
