package com.notify.notify.controller;

import com.notify.notify.model.request.AddBlacklistNumbersRequest;
import com.notify.notify.model.response.GenericResponse;
import com.notify.notify.constants.EndpointsConstants;
import com.notify.notify.constants.utility.UtilityConstants;
import com.notify.notify.service.BlackListNumbersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BlackListServiceController {
    private final BlackListNumbersService blackListNumbers;
    @PostMapping(EndpointsConstants.ADD_NUMBER_TO_BLACKLIST_ENDPOINT)
    public ResponseEntity addToBlackList(@Valid @RequestBody AddBlacklistNumbersRequest addBlacklistNumbersRequest) {
        log.info("Request received to add the number: {} in blacklist", addBlacklistNumbersRequest.getPhoneNumbers());
        for (Integer i = 0; i < addBlacklistNumbersRequest.getPhoneNumbers().size(); i++)
            blackListNumbers.add(addBlacklistNumbersRequest.getPhoneNumbers().get(i));
        GenericResponse<String> response = new GenericResponse<>();
        response.setData(UtilityConstants.SUCESSFULLY_BLACKLISTED);
        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping(EndpointsConstants.REMOVE_NUMBER_FROM_BLACKLIST_ENDPOINT)
    public ResponseEntity removeFromBlacklist(@PathVariable String number) {
        log.info("Request received to remove the number: {} from blacklist", number);
        blackListNumbers.remove(number);
        GenericResponse<String> response = new GenericResponse<>();
        response.setData(UtilityConstants.SUCESSFULLY_REMOVED_FROM_BLACKLIST);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(EndpointsConstants.FETCH_BLACKLIST_NUMBERS_ENDPOINT)
    public ResponseEntity getAllBlacklistedNumberNumbers() {
        log.info("Request received to get blacklisted numbers");
        GenericResponse<Set<String>> response = new GenericResponse<>();
        response.setData(blackListNumbers.getAllNumbers());
        return ResponseEntity.ok().body(response);
    }
}
