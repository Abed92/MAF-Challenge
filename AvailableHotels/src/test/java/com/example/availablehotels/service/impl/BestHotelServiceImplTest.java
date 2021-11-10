package com.example.availablehotels.service.impl;

import com.example.availablehotels.dao.HotelProviderDao;
import com.example.availablehotels.dto.ResponseDTO;
import com.example.availablehotels.exception.BaseSearchRequestException;
import com.example.availablehotels.model.BestHotel;
import com.example.availablehotels.webservice.base.BaseSearchRequest;
import com.example.availablehotels.webservice.providers.besthotel.BestHotelRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class BestHotelServiceImplTest {


    BaseSearchRequest request;


    @InjectMocks
    BestHotelServiceImpl bestAvailableHotelService;

    @Qualifier("BestHotelProviderDaoImpl")
    @MockBean
    HotelProviderDao bestHotelDao;

    @BeforeEach
    public void setUp() {
        bestAvailableHotelService = new BestHotelServiceImpl(bestHotelDao);
        this.request = new BestHotelRequest();
    }

    @Test
    public void shouldRetrieveMoreThanZeroItems() {
        //given
        this.request.setCity("aom");
        this.request.setFromDate(LocalDate.parse("2022-05-29"));
        this.request.setToDate(LocalDate.parse("2023-01-12"));
        this.request.setNumberOfAdults(90);


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

        when(bestHotelDao.getHotels(request)).thenReturn(Arrays.asList(bestHotel1));


        //when
        ResponseEntity<ResponseDTO> hotels = this.bestAvailableHotelService.getHotels(request);


        //then
        assertTrue(hotels.getBody().getResponse().size() > 0);


    }

    @Test
    public void shouldRetrieveZeroItems() {
        //given
        this.request.setCity("aom");
        this.request.setFromDate(LocalDate.parse("2022-05-29"));
        this.request.setToDate(LocalDate.parse("2023-01-12"));
        this.request.setNumberOfAdults(90);

        when(bestHotelDao.getHotels(request)).thenReturn(Arrays.asList());

        //when
        ResponseEntity<ResponseDTO> availableHotels = this.bestAvailableHotelService.getHotels(request);
        //then
        assertTrue(availableHotels.getBody().getResponse().size() == 0);


    }

    @Test
    public void shouldThrowExceptionIfRequestIsNotValid() {
        //given
        this.request.setCity("aotm");   // Invalid request input
        this.request.setFromDate(LocalDate.parse("2022-05-29"));
        this.request.setToDate(LocalDate.parse("2023-01-12"));
        this.request.setNumberOfAdults(90);
        //when
        Executable ex = () -> this.bestAvailableHotelService.getHotels(request);
        //then
        assertThrows(BaseSearchRequestException.class, ex);

    }

}