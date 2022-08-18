package com.notify.notify.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.notify.notify.enums.SmsStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sms {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("phone_number")
    @Column(name="phone_number")
    private String phoneNumber;

    @JsonProperty("message")
    private String message;
    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private SmsStatus status;

    @JsonProperty("failure_code")
    @Column(name = "failure_code")
    private String failureCode;

    @JsonProperty("failure_comments")
    @Column(name = "failure_comments")
    private String failureComments;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
