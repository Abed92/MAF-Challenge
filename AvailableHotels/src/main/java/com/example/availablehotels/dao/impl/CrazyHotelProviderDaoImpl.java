package com.example.availablehotels.dao.impl;

import com.example.availablehotels.dao.HotelProviderDao;
import com.example.availablehotels.model.CrazyHotel;
import com.example.availablehotels.webservice.base.BaseSearchRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Qualifier("CrazyHotelProviderDaoImpl")
@Repository
public class CrazyHotelProviderDaoImpl implements HotelProviderDao<CrazyHotel> {

    @Override
    public List<CrazyHotel> getHotels(BaseSearchRequest request) {

        request.isValid();
        List<CrazyHotel> crazyHotels = buildCrazyHotels();
        List<CrazyHotel> filterResult = searchResult(crazyHotels, request);
        return filterResult;
    }

    private List<CrazyHotel> searchResult(List<CrazyHotel> crazyHotels, BaseSearchRequest request) {

        if(crazyHotels.size()==0)
            return new ArrayList<CrazyHotel>();
        Predicate<CrazyHotel> city = item -> item.getCity().equalsIgnoreCase(request.getCity());
        Predicate<CrazyHotel> numberOfAdults = item -> item.getNumberOfAdults() == request.getNumberOfAdults();
        Predicate<CrazyHotel> fromDate = item -> item.getFromDate().equals((request.getFromDate().atStartOfDay(ZoneId.of("Asia/Amman")).toInstant().plus(1, ChronoUnit.DAYS)));
        Predicate<CrazyHotel> toDate = item -> item.getToDate().equals((request.getToDate().atStartOfDay(ZoneId.of("Asia/Amman")).toInstant().plus(1, ChronoUnit.DAYS)));


        List<CrazyHotel> resultLists = crazyHotels.stream()
                .filter(city
                        .or(numberOfAdults)
                        .or(fromDate)
                        .or(toDate))
                .collect(Collectors.toList());
        return resultLists;

    }


    private List<CrazyHotel> buildCrazyHotels() {


        CrazyHotel crazyHotel1 = new CrazyHotel.CrazyHotelBuilder()
                .withProviderName("Crazy Hotel")
                .withHotelName("crazyHotel1")
                .withCity("AHU")
                .withFromDate(Instant.parse("2022-12-23T00:00:00Z"))
                .withNumberOfAdults(2)
                .withToDate(Instant.parse("2023-02-23T00:00:00Z"))
                .withPrice(53.0)
                .withRate("***")
                .withRoomAmenities(new String[]{"swimming bool", "TV with cable", "Hair Drier"})
                .build();

        CrazyHotel crazyHotel2 = new CrazyHotel.CrazyHotelBuilder().withHotelName("crazyHotel2")
                .withCity("Amm")
                .withFromDate(Instant.parse("2022-05-25T21:00:00Z"))
                .withNumberOfAdults(1)
                .withToDate(Instant.parse("2023-01-03T22:00:00Z"))
                .withPrice(89.0)
                .withRate("****")
                .withRoomAmenities(new String[]{"swimming bool", "TV with cable"})
                .withProviderName("Crazy Hotel")
                .withRoomDiscount(0.6)
                .build();

        CrazyHotel crazyHotel3 = new CrazyHotel.CrazyHotelBuilder().withHotelName("crazyHotel3")
                .withCity("Amm")
                .withFromDate(Instant.parse("2020-05-25T00:00:00Z"))
                .withNumberOfAdults(1)
                .withToDate(Instant.parse("2021-01-03T00:00:00Z"))
                .withPrice(89.0)
                .withRate("*****")
                .withRoomAmenities(new String[]{"swimming bool", "TV with cable"})
                .withProviderName("Crazy Hotel")
                .withRoomDiscount(0.9)
                .build();

        List<CrazyHotel> crazyHotels = new ArrayList<>();
        crazyHotels.add(crazyHotel1);
        crazyHotels.add(crazyHotel2);
        crazyHotels.add(crazyHotel3);


        return crazyHotels;

    }
}
