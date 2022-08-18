package com.notify.notify.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendSmsResponse {
    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("comments")
    private String comments;

}
