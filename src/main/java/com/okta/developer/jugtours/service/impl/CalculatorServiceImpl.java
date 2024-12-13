package com.okta.developer.jugtours.service.impl;

import com.okta.developer.jugtours.model.request.BillData;
import com.okta.developer.jugtours.model.request.CalculatorRequest;
import com.okta.developer.jugtours.model.request.PlayerInfo;
import com.okta.developer.jugtours.model.response.IndividualBill;
import com.okta.developer.jugtours.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CalculatorServiceImpl implements CalculatorService {
    private final Logger log = LoggerFactory.getLogger(CalculatorServiceImpl.class);

    public static String convertToHHMM(String timestamp) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_DATE_TIME;
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(timestamp, inputFormatter);
        return zonedDateTime.format(outputFormatter);
    }

    public static List<String> getMinutesBetween(String timeFrom, String timeTo) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime end = LocalDateTime.parse(timeTo, formatter);

        List<String> minutesList = new ArrayList<>();
        LocalDateTime current = start.plusMinutes(1).withSecond(0).withNano(0);

        while (!current.isAfter(end)) {
            minutesList.add(current.format(DateTimeFormatter.ofPattern("HH:mm")));
            current = current.plusMinutes(1);
        }

        return minutesList;
    }


    @Override
    public Object calculateIndividualBill (CalculatorRequest request) {
        //define
        List<IndividualBill> listResponseBill = new ArrayList<>();
        BillData bill = request.getBillData();
        List<PlayerInfo> listP = request.getPlayers();
        List<List<String>> listTimePl = new ArrayList<>();
        for(int i = 0 ; i < request.getPlayers().size();i++){
            List<String> pMin = initListMinutes(listP.get(i).getTimeFrom(), listP.get(i).getTimeTo());
            listTimePl.add(pMin);
            IndividualBill individualBill = new IndividualBill();
            individualBill.setId(i+1);
            individualBill.setName(listP.get(i).getPlayerName());
            individualBill.setFromTime(listP.get(i).getTimeFrom());
            individualBill.setToTime(listP.get(i).getTimeTo());
            individualBill.setServiceFee(listP.get(i).getServiceFee());
            listResponseBill.add(individualBill);
        }

        // calculate total mins
//        int totalMin = calculateMinuteDifference(bill.getFromDate(),bill.getToDate());
        BigDecimal hourBill = request.getBillData().getAmount();
        // calculate price per min ( 1 p)
        BigDecimal pricePerMin = hourBill.divide(new BigDecimal(60),MathContext.DECIMAL32);

        // add all list min into single one for count point
        List<String> listMergeMins = new ArrayList<>();
        for (int j = 0; j < listTimePl.size(); j++) {
            listMergeMins.addAll(listTimePl.get(j));
            log.info(j+ "    " + listTimePl.get(j).toString());
        }
        log.info("List merge min : "+ listMergeMins);
        // declare point for min real
        Map<String,BigDecimal>listMinWithPoint = new LinkedHashMap <>();
        for(String s : listMergeMins){
            Integer count = Collections.frequency(listMergeMins, s);
            BigDecimal priceMinReal = pricePerMin.divide(new BigDecimal(count),MathContext.DECIMAL32);
            listMinWithPoint.put(s,priceMinReal);
        }
        log.info("Map min with POINT listMinWithPoint : " + listMinWithPoint);
        // calculate price per player
        // re-calculate price per min with point
        List<Map<String,BigDecimal>> listPriceFinal = new ArrayList<>();
        for(int i = 0 ; i < listTimePl.size();i++){
            List<String> plTemp = listTimePl.get(i);
            BigDecimal finalPrice = new BigDecimal(BigInteger.ZERO);
            for( int j = 0 ; j < plTemp.size();j++){
                finalPrice = finalPrice.add(listMinWithPoint.get(plTemp.get(j))).setScale(0,BigDecimal.ROUND_HALF_UP);
            }
            listResponseBill.get(i).setPrice(finalPrice.add(listResponseBill.get(i).getServiceFee()).setScale(0,BigDecimal.ROUND_HALF_UP));
            listResponseBill.get(i).setDetail("TOTAL: " + listResponseBill.get(i).getPrice()+" VND ---------TIME's FEE: "+ finalPrice+" VND --------- SERVICE's FEE: " + listResponseBill.get(i).getServiceFee() + " VND" );
            log.info("Price of player "+(i+1)+"   : " + finalPrice);
        }
        return listResponseBill;
    }

    // Create a list with minutes of player
    public List<String> initListMinutes (LocalDateTime fromTime , LocalDateTime toTime){
        List<String> pMins = new ArrayList<>();
        pMins.addAll(getMinutesBetween(fromTime.toString(), toTime.toString()));
        return pMins;
    }
}
