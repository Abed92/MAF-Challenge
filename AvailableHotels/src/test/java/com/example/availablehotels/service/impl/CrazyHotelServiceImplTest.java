package com.example.availablehotels.service.impl;

import com.example.availablehotels.dao.HotelProviderDao;
import com.example.availablehotels.dto.ResponseDTO;
import com.example.availablehotels.exception.BaseSearchRequestException;
import com.example.availablehotels.model.CrazyHotel;
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

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class CrazyHotelServiceImplTest {



    BaseSearchRequest request;


    @InjectMocks
    CrazyHotelServiceImpl crazyHotelService ;

    @Qualifier("BestHotelProviderDaoImpl")
    @MockBean
    HotelProviderDao crazyHotelDao;

    @BeforeEach
    public void setUp() {
        crazyHotelService = new CrazyHotelServiceImpl(crazyHotelDao);
        this.request = new BestHotelRequest();
    }

    @Test
    public void shouldRetrieveMoreThanZeroItems() {
        //given
        this.request.setCity("aom");
        this.request.setFromDate(LocalDate.parse("2022-05-29"));
        this.request.setToDate(LocalDate.parse("2023-01-12"));
        this.request.setNumberOfAdults(90);


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
        when(crazyHotelDao.getHotels(request)).thenReturn(Arrays.asList(crazyHotel1));


        //when
        ResponseEntity<ResponseDTO> hotels = this.crazyHotelService.getHotels(request);


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

        when(crazyHotelDao.getHotels(request)).thenReturn(Arrays.asList());

        //when
        ResponseEntity<ResponseDTO> availableHotels = this.crazyHotelService.getHotels(request);
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
        Executable ex = () -> this.crazyHotelService.getHotels(request);
        //then
        assertThrows(BaseSearchRequestException.class, ex);

    }

}