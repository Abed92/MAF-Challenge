package com.example.availablehotels.dao.impl;

import com.example.availablehotels.exception.BaseSearchRequestException;
import com.example.availablehotels.model.BestHotel;
import com.example.availablehotels.webservice.base.BaseSearchRequest;
import com.example.availablehotels.webservice.providers.besthotel.BestHotelRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BestHotelProviderDaoImplTest {


    BestHotelProviderDaoImpl bestHotelProviderDao;
    BaseSearchRequest request ;
    @BeforeEach
    public void setUp()
    {
        bestHotelProviderDao = new BestHotelProviderDaoImpl();
        this.request = new BestHotelRequest();
    }
    @Test
    public void shouldRetrieveMoreThanZeroItems()
    {
        //given
        this.request.setCity("amm");
        this.request.setFromDate(LocalDate.parse("2022-05-25"));
        this.request.setToDate(LocalDate.parse("2023-01-03"));
        this.request.setNumberOfAdults(1);
        //when
        List<BestHotel> hotels = this.bestHotelProviderDao.getHotels(request);

        //then
        assertTrue(hotels.size()>0);


    }

    @Test
    public void shouldRetrieveZeroItems()
    {
        //given
        this.request.setCity("aom");
        this.request.setFromDate(LocalDate.parse("2022-05-29"));
        this.request.setToDate(LocalDate.parse("2023-01-12"));
        this.request.setNumberOfAdults(90);
        //when
        List<BestHotel> hotels = this.bestHotelProviderDao.getHotels(request);

        //then
        assertTrue(hotels.size()==0);


    }

    @Test
    public void shouldThrowExceptionIfRequestIsNotValid()
    {
        //given
        this.request.setCity("aotm");   // Invalid request input
        this.request.setFromDate(LocalDate.parse("2022-05-29"));
        this.request.setToDate(LocalDate.parse("2023-01-12"));
        this.request.setNumberOfAdults(90);
        //when
        Executable ex = ()-> this.bestHotelProviderDao.getHotels(request);
        //then
        assertThrows(BaseSearchRequestException.class,ex);

    }

}