package com.okta.developer.jugtours.service;

import com.okta.developer.jugtours.model.request.CalculatorRequest;
import com.okta.developer.jugtours.model.response.IndividualBill;

import java.util.List;
public interface CalculatorService {
    Object calculateIndividualBill (CalculatorRequest request);
}
