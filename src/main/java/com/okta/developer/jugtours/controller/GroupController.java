package com.okta.developer.jugtours.controller;

import com.okta.developer.jugtours.model.Group;
import com.okta.developer.jugtours.model.request.CalculatorRequest;
import com.okta.developer.jugtours.model.response.ApiResponse;
import com.okta.developer.jugtours.model.response.IndividualBill;
import com.okta.developer.jugtours.service.CalculatorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
class GroupController {

    private final Logger log = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    CalculatorService calculatorService;

    @PostMapping("/calculator")
    ResponseEntity<ApiResponse<?>> createGroup(@Valid @RequestBody CalculatorRequest request)  {
        log.info("Request to calculate: {}", request);
        Object resultList = calculatorService.calculateIndividualBill(request);
        return ResponseEntity.ok(ApiResponse.success(resultList));
    }
}