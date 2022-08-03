package com.notify.notify.controller;

import com.notify.notify.model.Message;
import com.notify.notify.model.SMS;
import com.notify.notify.model.request.SendSMSRequest;
import com.notify.notify.model.request.AddBlacklistNumbersRequest;
import com.notify.notify.model.request.TextSearchRequest;
import com.notify.notify.model.request.TimeSearchRequest;
import com.notify.notify.model.response.GenericResponse;
import com.notify.notify.model.response.SendSMSResponse;
import com.notify.notify.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;


@RestController
@Slf4j
public class MyController {



    @Autowired
    private SMSCRUDService smsCRUDService;

    @Autowired
    private SMSqueueService smsQueueService;

    @Autowired
    private BlackListNumbersService blackListNumbers;

    @Autowired
    private SMSSearchService smsSearchService;

    @PostMapping("/v1/sms/send")
    public ResponseEntity sendSMS(@RequestBody SendSMSRequest sendSmsRequest)
    {
        log.info("Request received for send sms");
        Long messageId = smsCRUDService.saveSMS(sendSmsRequest);
        String stringMessageId = String.valueOf(messageId);
        smsQueueService.queueMessageId(stringMessageId);
        GenericResponse<SendSMSResponse> response = new GenericResponse<SendSMSResponse>();
        SendSMSResponse data = new SendSMSResponse();
        data.setRequestId(stringMessageId);
        data.setComments("Successfully Sent");
        response.setData(data);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/v1/blacklist")
    public ResponseEntity addToBlackList(@RequestBody AddBlacklistNumbersRequest addBlacklistNumbersRequest)
    {
        log.info("Request received to add the number: {} in blacklist",addBlacklistNumbersRequest.getPhoneNumbers());
        for(Integer i=0;i<addBlacklistNumbersRequest.getPhoneNumbers().size();i++)
            blackListNumbers.add(addBlacklistNumbersRequest.getPhoneNumbers().get(i));
        GenericResponse <String> response = new GenericResponse<String>();
        response.setData("Successfully blacklisted");
        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping("/v1/blacklist/{number}")
    public ResponseEntity remove(@PathVariable String number)
    {
        log.info("Request received to remove the number: {} from blacklist",number);
        blackListNumbers.remove(number);
        GenericResponse <String> response = new GenericResponse<String>();
        response.setData("Successfully removed from blacklist");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/v1/blacklist")
    public ResponseEntity getAllNumbers()
    {
        log.info("Request received to get blacklisted numbers");
        GenericResponse<Set<String>> response = new GenericResponse<Set<String>>();
        response.setData(blackListNumbers.getAllNumbers());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/v1/sms/{smsId}")
    public ResponseEntity getSMSById(@PathVariable String smsId)
    {
        log.info("Request received to get sms details corresponds to smsId: {}",smsId);
        GenericResponse<SMS> response = new GenericResponse<SMS>();
        response.setData(smsCRUDService.getSMSById(Long.parseLong(smsId)));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/v1/timeSearch")
    public ResponseEntity searchTimeRange(@RequestBody TimeSearchRequest timeSearchRequest)
    {
        log.info("Request received for search sms in the given time interval");
        GenericResponse<List<Message> > response = new GenericResponse<List<Message> >();
        response.setData(smsSearchService.timeRangeSearch(timeSearchRequest));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/v1/textSearch")
    public ResponseEntity searchText(@RequestBody TextSearchRequest textSearchRequest)
    {
        log.info("Request received for search sms containing the given text");
        GenericResponse<List<Message>> response = new GenericResponse<List<Message> >();
        response.setData(smsSearchService.textSearch(textSearchRequest));
        return ResponseEntity.ok().body(response);
    }

}