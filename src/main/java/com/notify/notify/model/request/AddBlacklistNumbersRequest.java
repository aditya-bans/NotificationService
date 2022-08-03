package com.notify.notify.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AddBlacklistNumbersRequest {

    @JsonProperty("phone_numbers")
    private List<String> phoneNumbers;
}
