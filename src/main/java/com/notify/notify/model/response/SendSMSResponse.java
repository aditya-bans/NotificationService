package com.notify.notify.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendSMSResponse {
    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("comments")
    private String comments;

}
