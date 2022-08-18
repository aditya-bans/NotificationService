package com.notify.notify.controller;

import com.notify.notify.model.request.SearchTextRequest;
import com.notify.notify.model.request.SearchTimeRequest;
import com.notify.notify.model.response.GenericResponse;
import com.notify.notify.constants.EndpointsConstants;
import com.notify.notify.model.SmsDataForElasticsearch;
import com.notify.notify.service.SmsSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SearchSmsController {
    private final SmsSearchService smsSearchService;
    @GetMapping(EndpointsConstants.SEARCH_SMS_FOR_TIME_INTERVAL_ENDPOINT)
    public ResponseEntity searchSmsForTimeRange(@Valid @RequestBody SearchTimeRequest searchTimeRequest) {
        log.info("Request received for search sms in the given time interval");
        log.info("{}",searchTimeRequest);
        GenericResponse<List<SmsDataForElasticsearch>> response = new GenericResponse<>();
        response.setData(smsSearchService.searchSmsForTimeRange(searchTimeRequest));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(EndpointsConstants.SEARCH_SMS_FOR_TEXT_ENDPOINT)
    public ResponseEntity searchSmsForText(@RequestBody SearchTextRequest searchTextRequest) {
        log.info("Request received for search sms containing the given text");
        GenericResponse<List<SmsDataForElasticsearch>> response = new GenericResponse<>();
        response.setData(smsSearchService.searchSmsForText(searchTextRequest));
        return ResponseEntity.ok().body(response);
    }
}
