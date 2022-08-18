package com.notify.notify.model.request.imiApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImiApiDestination {
    @JsonProperty("msisdn")
    private List<String> msisdn;
    @JsonProperty("correlationid")
    private String correlationId;
}
