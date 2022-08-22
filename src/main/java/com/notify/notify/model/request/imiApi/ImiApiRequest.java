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
public class ImiApiRequest {
    @JsonProperty("deliverychannel")
    private String deliveryChannel;
    @JsonProperty("channels")
    private ImiApiChannels imiApiChannels;
    @JsonProperty("destination")
    private List<ImiApiDestination> destination;
}
