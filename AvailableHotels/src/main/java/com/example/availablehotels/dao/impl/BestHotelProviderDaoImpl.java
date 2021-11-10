package com.example.availablehotels.dao.impl;

import com.example.availablehotels.dao.HotelProviderDao;
import com.example.availablehotels.model.BestHotel;
import com.example.availablehotels.webservice.base.BaseSearchRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Qualifier("BestHotelProviderDaoImpl")
@Repository
public class BestHotelProviderDaoImpl implements HotelProviderDao<BestHotel> {

    @Override
    public List<BestHotel> getHotels(BaseSearchRequest request) {

        request.isValid();
        List<BestHotel> crazyHotels = buildBestHotels();
        List<BestHotel> filterResult = searchResult(crazyHotels, request);
        return filterResult;
    }

    private List<BestHotel> searchResult(List<BestHotel> bestHotel, BaseSearchRequest request) {


        if(bestHotel.size()==0)
            return new ArrayList<BestHotel>();
        Predicate<BestHotel> city = item -> item.getCity().equalsIgnoreCase(request.getCity());
        Predicate<BestHotel> numberOfAdults = item -> item.getNumberOfAdults() == request.getNumberOfAdults();
        Predicate<BestHotel> fromDate = item -> item.getFromDate().equals((request.getFromDate()));
        Predicate<BestHotel> toDate = item -> item.getToDate().equals((request.getToDate()));


        List<BestHotel> resultLists = bestHotel.stream()
                .filter(city
                        .or(numberOfAdults)
                        .or(fromDate)
                        .or(toDate))
                .collect(Collectors.toList());
        return resultLists;

    }


    private List<BestHotel> buildBestHotels() {


        BestHotel bestHotel1 = new BestHotel.BestHotelBuilder()
                .withProviderName("Best Hotel")
                .withHotelName("bestHotel1")
                .withCity("AHU")
                .withFromDate(LocalDate.parse("2022-12-23"))
                .withNumberOfAdults(2)
                .withToDate(LocalDate.parse("2023-02-23"))
                .withPrice(53.0)
                .withRate(3)
                .withRoomAmenities("swimming bool , TV with cable, Hair Drier")
                .build();

        BestHotel bestHotel2 = new BestHotel.BestHotelBuilder().withHotelName("bestHotel2")
                .withCity("Amm")
                .withFromDate(LocalDate.parse("2022-05-25"))
                .withNumberOfAdults(1)
                .withToDate(LocalDate.parse("2023-01-03"))
                .withPrice(89.0)
                .withRate(4)
                .withRoomAmenities("swimming bool,TV with cable")
                .withProviderName("Best Hotel")
                .build();

        BestHotel bestHotel3 = new BestHotel.BestHotelBuilder().withHotelName("bestHotel3")
                .withCity("Amm")
                .withFromDate(LocalDate.parse("2020-05-25"))
                .withNumberOfAdults(1)
                .withToDate(LocalDate.parse("2021-01-03"))
                .withPrice(89.0)
                .withRate(5)
                .withRoomAmenities("swimming bool,TV with cable")
                .withProviderName("Best Hotel")
                .build();

        List<BestHotel> crazyHotels = new ArrayList<>();
        crazyHotels.add(bestHotel1);
        crazyHotels.add(bestHotel2);
        crazyHotels.add(bestHotel3);


        return crazyHotels;

    }


}
