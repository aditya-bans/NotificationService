package com.notify.notify.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchTimeRequest {
    @JsonProperty("phone_number")
    @NotBlank(message = "phone_number is mandatory")
    @NotNull(message = "phone_number is mandatory")
    @Pattern(regexp = "^((\\+){1}91){1}[1-9]{1}[0-9]{9}$", message = "phone_number should start with +91 and should have 10 more digits")
    private String phoneNumber;

    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    private LocalDateTime endTime;


}
