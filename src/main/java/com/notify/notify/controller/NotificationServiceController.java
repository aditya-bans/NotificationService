package com.notify.notify.controller;

import com.notify.notify.model.request.SendSmsRequest;
import com.notify.notify.model.response.GenericResponse;
import com.notify.notify.model.response.SendSmsResponse;
import com.notify.notify.constants.EndpointsConstants;
import com.notify.notify.constants.utility.UtilityConstants;
import com.notify.notify.model.Sms;
import com.notify.notify.service.SendSmsService;
import com.notify.notify.service.SmsCRUDService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NotificationServiceController {
    private final SendSmsService sendSmsService;
    private final SmsCRUDService smsCRUDService;

    @PostMapping(EndpointsConstants.SEND_SMS_ENDPOINT)
    public ResponseEntity sendSMS(@Valid @RequestBody SendSmsRequest sendSmsRequest) {
        log.info("Request received for send sms");
        GenericResponse<SendSmsResponse> response = new GenericResponse<>();
        SendSmsResponse data = new SendSmsResponse();
        data.setRequestId(sendSmsService.sendSms(sendSmsRequest));
        data.setComments(UtilityConstants.SUCCESSFULLY_SENT);
        response.setData(data);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping(EndpointsConstants.FETCH_SMS_BY_ID_ENDPOINT)
    public ResponseEntity getSMSById(@PathVariable String smsId) {
        log.info("Request received to get sms details corresponds to smsId: {}", smsId);
        GenericResponse<Sms> response = new GenericResponse<>();
        response.setData(smsCRUDService.getSMSById(Long.parseLong(smsId)));
        return ResponseEntity.ok().body(response);
    }
}
