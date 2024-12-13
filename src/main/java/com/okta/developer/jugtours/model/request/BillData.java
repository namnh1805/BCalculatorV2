package com.okta.developer.jugtours.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BillData implements Serializable {
    String name;
    @NotNull(message = "Cant not be null")
    BigDecimal amount;
    String description;
    LocalDateTime fromDate;
    LocalDateTime toDate;
}
