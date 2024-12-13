package com.okta.developer.jugtours.model.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PlayerInfo implements Serializable {
    Integer id ;
    String playerName;
    LocalDateTime timeFrom;
    LocalDateTime timeTo;
    BigDecimal serviceFee;
}
