package com.notify.notify.model.response.imiApi;

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
public class ImiApiResponse {
    @JsonProperty("response")
    private List<ImiApiCodeResponse> response;
}
