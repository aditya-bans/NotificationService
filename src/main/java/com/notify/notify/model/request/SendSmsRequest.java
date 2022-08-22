package com.notify.notify.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.notify.notify.constants.utility.UtilityConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendSmsRequest {

    @JsonProperty("phone_number")
    @NotBlank(message = UtilityConstants.MANDATORY_PHONENUMBER)
    @NotNull(message = UtilityConstants.MANDATORY_PHONENUMBER)
    @Pattern(regexp = "^((\\+){1}91){1}[1-9]{1}[0-9]{9}$", message = UtilityConstants.VALID_PHONENUMBER_COMMENT)
    private String phoneNumber;

    @JsonProperty("message")
    private String message;

}
