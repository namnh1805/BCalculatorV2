package com.okta.developer.jugtours.model.request;

import jakarta.validation.Valid;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Valid
public class CalculatorRequest implements Serializable {
    List<PlayerInfo> players;
    @Valid
    BillData billData;
}
