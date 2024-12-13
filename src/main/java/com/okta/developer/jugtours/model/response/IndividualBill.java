package com.okta.developer.jugtours.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class IndividualBill {
    Integer id;
    String name;
    BigDecimal price;
    BigDecimal serviceFee;
    LocalDateTime fromTime;
    LocalDateTime toTime;
    String detail;
}
