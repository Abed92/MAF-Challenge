package com.example.availablehotels.controller;

import com.example.availablehotels.dto.AvailableHotelResponseDTO;
import com.example.availablehotels.dto.BestHotelResponseDTO;
import com.example.availablehotels.dto.CrazyHotelResponseDTO;
import com.example.availablehotels.dto.ResponseDTO;
import com.example.availablehotels.exception.BaseSearchRequestException;
import com.example.availablehotels.model.BestHotel;
import com.example.availablehotels.model.CrazyHotel;
import com.example.availablehotels.service.AvailableHotelService;
import com.example.availablehotels.service.HotelProviderService;
import com.example.availablehotels.webservice.availablehotel.AvailableHotelRequest;
import com.example.availablehotels.webservice.availablehotel.AvailableHotelResponse;
import com.example.availablehotels.webservice.base.BaseSearchRequest;
import com.example.availablehotels.webservice.providers.besthotel.BestHotelRequest;
import com.example.availablehotels.webservice.providers.besthotel.BestHotelResponse;
import com.example.availablehotels.webservice.providers.crazyhotel.CrazyHotelResponse;
import com.example.availablehotels.webservice.providers.crazyhotel.CrazyHotelsRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@SpringBootTest
class AvailableHotelControllerTest {

    @InjectMocks
    AvailableHotelController availableHotelController;

    @MockBean
    @Qualifier("BestHotelServiceImpl")
    HotelProviderService bestHotelProviderService;

    @MockBean
    @Qualifier("CrazyHotelServiceImpl")
    HotelProviderService crazyHotelProviderService;

    @MockBean
    AvailableHotelService availableHotelService;

    BestHotelRequest requestForBestHotelService;
    CrazyHotelsRequest requestForCrazyHotelService;
    AvailableHotelRequest requestForAllAvailableHotelService;


    @BeforeEach
    void setUp() {
        this.requestForAllAvailableHotelService = new AvailableHotelRequest();
        this.requestForBestHotelService = new BestHotelRequest();
        this.requestForCrazyHotelService = new CrazyHotelsRequest();
        availableHotelController = new AvailableHotelController(crazyHotelProviderService, bestHotelProviderService, availableHotelService);
    }


// Tests For BestHotel

    @Test
    public void ShouldReturnMoreThanZeroBestHotel() {
        //given
        this.requestForBestHotelService.setCity("amm");
        this.requestForBestHotelService.setFromDate(LocalDate.parse("2022-05-29"));
        this.requestForBestHotelService.setToDate(LocalDate.parse("2023-01-12"));
        this.requestForBestHotelService.setNumberOfAdults(90);


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

        BestHotelResponseDTO bestHotelResponseDTO = new BestHotelResponseDTO();
        bestHotelResponseDTO.setHotelName(bestHotel1.getHotelName());
        bestHotelResponseDTO.setHotelFare(bestHotel1.getHotelFare());
        bestHotelResponseDTO.setRoomAmenities(bestHotel1.getRoomAmenities());
        bestHotelResponseDTO.setHotelRate(String.valueOf(bestHotel1.getHotelRate()));
        ResponseDTO<BestHotelResponseDTO> bestHotelResponse = new BestHotelResponse();
        bestHotelResponse.setResponse(Arrays.asList(bestHotelResponseDTO));

        when(bestHotelProviderService.getHotels(requestForBestHotelService)).thenReturn(new ResponseEntity<>(bestHotelResponse, HttpStatus.OK));

        //when
        ResponseEntity<ResponseDTO> responseEntity = availableHotelController.bestHotel(this.requestForBestHotelService);

        assertTrue(responseEntity.getBody().getResponse().size() > 0);


    }

    @Test
    public void ShouldReturnZeroBestHotel() {
        //given
        this.requestForBestHotelService.setCity("aom");
        this.requestForBestHotelService.setFromDate(LocalDate.parse("2022-05-29"));
        this.requestForBestHotelService.setToDate(LocalDate.parse("2023-01-12"));
        this.requestForBestHotelService.setNumberOfAdults(90);


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

        BestHotelResponseDTO bestHotelResponseDTO = new BestHotelResponseDTO();
        bestHotelResponseDTO.setHotelName(bestHotel1.getHotelName());
        bestHotelResponseDTO.setHotelFare(bestHotel1.getHotelFare());
        bestHotelResponseDTO.setRoomAmenities(bestHotel1.getRoomAmenities());
        bestHotelResponseDTO.setHotelRate(String.valueOf(bestHotel1.getHotelRate()));
        ResponseDTO<BestHotelResponseDTO> bestHotelResponse = new BestHotelResponse();
        bestHotelResponse.setResponse(Arrays.asList());

        when(bestHotelProviderService.getHotels(requestForBestHotelService)).thenReturn(new ResponseEntity<>(bestHotelResponse, HttpStatus.OK));

        //when
        ResponseEntity<ResponseDTO> responseEntity = availableHotelController.bestHotel(this.requestForBestHotelService);

        assertTrue(responseEntity.getBody().getResponse().size() == 0);

    }

    @Test
    public void ShouldThrowExceptionWhenRequestNotValid() {
        //given
        this.requestForBestHotelService.setCity("aomm");
        this.requestForBestHotelService.setFromDate(LocalDate.parse("2022-05-29"));
        this.requestForBestHotelService.setToDate(LocalDate.parse("2023-01-12"));
        this.requestForBestHotelService.setNumberOfAdults(90);


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

        BestHotelResponseDTO bestHotelResponseDTO = new BestHotelResponseDTO();
        bestHotelResponseDTO.setHotelName(bestHotel1.getHotelName());
        bestHotelResponseDTO.setHotelFare(bestHotel1.getHotelFare());
        bestHotelResponseDTO.setRoomAmenities(bestHotel1.getRoomAmenities());
        bestHotelResponseDTO.setHotelRate(String.valueOf(bestHotel1.getHotelRate()));
        ResponseDTO<BestHotelResponseDTO> bestHotelResponse = new BestHotelResponse();
        bestHotelResponse.setResponse(Arrays.asList());

        when(bestHotelProviderService.getHotels(requestForBestHotelService)).thenReturn(new ResponseEntity<>(bestHotelResponse, HttpStatus.OK));

        //when
        Executable ex = () -> availableHotelController.bestHotel(this.requestForBestHotelService);

        assertThrows(BaseSearchRequestException.class, ex);

    }

    //////////////////////////////////////////////////////////
    // Test For Crazy Hotel


    @Test
    public void ShouldReturnMoreThanZeroCrazyHotel() {
        //given
        this.requestForCrazyHotelService.setCity("amm");
        this.requestForCrazyHotelService.setFromDate(LocalDate.parse("2022-05-29"));
        this.requestForCrazyHotelService.setToDate(LocalDate.parse("2023-01-12"));
        this.requestForCrazyHotelService.setNumberOfAdults(90);


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

        CrazyHotelResponseDTO bestHotelResponseDTO = new CrazyHotelResponseDTO();
        bestHotelResponseDTO.setHotelName(crazyHotel1.getHotelName());
        bestHotelResponseDTO.setPrice(crazyHotel1.getPrice());
        bestHotelResponseDTO.setAmenities(crazyHotel1.getAmenities());
        bestHotelResponseDTO.setRate(String.valueOf(crazyHotel1.getRate()));
        ResponseDTO<CrazyHotelResponseDTO> crazyHotelResponse = new CrazyHotelResponse();
        crazyHotelResponse.setResponse(Arrays.asList(bestHotelResponseDTO));

        when(crazyHotelProviderService.getHotels(requestForCrazyHotelService)).thenReturn(new ResponseEntity<>(crazyHotelResponse, HttpStatus.OK));

        //when
        ResponseEntity<ResponseDTO> responseEntity = availableHotelController.crazyHotel(this.requestForCrazyHotelService);

        assertTrue(responseEntity.getBody().getResponse().size() > 0);


    }


    @Test
    public void ShouldReturnZeroCrazyHotel() {
        //given
        this.requestForCrazyHotelService.setCity("aom");
        this.requestForCrazyHotelService.setFromDate(LocalDate.parse("2022-05-29"));
        this.requestForCrazyHotelService.setToDate(LocalDate.parse("2023-01-12"));
        this.requestForCrazyHotelService.setNumberOfAdults(90);


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

        CrazyHotelResponseDTO bestHotelResponseDTO = new CrazyHotelResponseDTO();
        bestHotelResponseDTO.setHotelName(crazyHotel1.getHotelName());
        bestHotelResponseDTO.setPrice(crazyHotel1.getPrice());
        bestHotelResponseDTO.setAmenities(crazyHotel1.getAmenities());
        bestHotelResponseDTO.setRate(String.valueOf(crazyHotel1.getRate()));
        ResponseDTO<CrazyHotelResponseDTO> crazyHotelResponse = new CrazyHotelResponse();
        crazyHotelResponse.setResponse(Arrays.asList());


        when(crazyHotelProviderService.getHotels(requestForCrazyHotelService)).thenReturn(new ResponseEntity<>(crazyHotelResponse, HttpStatus.OK));

        //when
        ResponseEntity<ResponseDTO> responseEntity = availableHotelController.crazyHotel(this.requestForCrazyHotelService);

        assertTrue(responseEntity.getBody().getResponse().size() == 0);


    }


    @Test
    public void shouldThrowExceptionIfRequestIsNotValid() {
        //given
        this.requestForCrazyHotelService.setCity("aomm");
        this.requestForCrazyHotelService.setFromDate(LocalDate.parse("2022-05-29"));
        this.requestForCrazyHotelService.setToDate(LocalDate.parse("2023-01-12"));
        this.requestForCrazyHotelService.setNumberOfAdults(90);


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

        CrazyHotelResponseDTO bestHotelResponseDTO = new CrazyHotelResponseDTO();
        bestHotelResponseDTO.setHotelName(crazyHotel1.getHotelName());
        bestHotelResponseDTO.setPrice(crazyHotel1.getPrice());
        bestHotelResponseDTO.setAmenities(crazyHotel1.getAmenities());
        bestHotelResponseDTO.setRate(String.valueOf(crazyHotel1.getRate()));
        ResponseDTO<CrazyHotelResponseDTO> crazyHotelResponse = new CrazyHotelResponse();
        crazyHotelResponse.setResponse(Arrays.asList(bestHotelResponseDTO));


        when(crazyHotelProviderService.getHotels(requestForCrazyHotelService)).thenReturn(new ResponseEntity<>(crazyHotelResponse, HttpStatus.OK));

        //when
        Executable ex = () -> availableHotelController.crazyHotel(this.requestForCrazyHotelService);

        assertThrows(BaseSearchRequestException.class, ex);


    }

    ///////////////////////////////////////////////
    // Test For All Available Hotels

    @Test
    public void ShouldReturnMoreThanZeroOfAvailableHotels() {
        //given
        this.requestForAllAvailableHotelService.setCity("amm");
        this.requestForAllAvailableHotelService.setFromDate(LocalDate.parse("2022-05-29"));
        this.requestForAllAvailableHotelService.setToDate(LocalDate.parse("2023-01-12"));
        this.requestForAllAvailableHotelService.setNumberOfAdults(90);


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

        AvailableHotelResponseDTO availableHotelResponseDTOFromBest = new AvailableHotelResponseDTO();
        availableHotelResponseDTOFromBest.setProviderName(bestHotel1.getProviderName());
        availableHotelResponseDTOFromBest.setHotelName(bestHotel1.getHotelName());
        availableHotelResponseDTOFromBest.setAmenities(bestHotel1.getRoomAmenities().split(","));
        availableHotelResponseDTOFromBest.setPrice(bestHotel1.getHotelFare());

        AvailableHotelResponseDTO availableHotelResponseDTOFromCrazy = new AvailableHotelResponseDTO();
        availableHotelResponseDTOFromCrazy.setPrice(crazyHotel1.getPrice());
        availableHotelResponseDTOFromCrazy.setHotelName(crazyHotel1.getHotelName());
        availableHotelResponseDTOFromCrazy.setAmenities(crazyHotel1.getAmenities());
        availableHotelResponseDTOFromCrazy.setProviderName(crazyHotel1.getProviderName());
        AvailableHotelResponse availableHotelResponse = new AvailableHotelResponse();
        availableHotelResponse.setResponse(Arrays.asList(availableHotelResponseDTOFromBest,availableHotelResponseDTOFromCrazy));


        when(availableHotelService.getAvailableHotels(requestForAllAvailableHotelService)).thenReturn(new ResponseEntity<>(availableHotelResponse, HttpStatus.OK));

        //when
        ResponseEntity<ResponseDTO> responseEntity = availableHotelController.availableHotel(this.requestForAllAvailableHotelService);

        assertTrue(responseEntity.getBody().getResponse().size() > 0);


    }


    @Test
    public void ShouldReturnZeroOfAvailableHotels() {
        //given
        this.requestForAllAvailableHotelService.setCity("aom");
        this.requestForAllAvailableHotelService.setFromDate(LocalDate.parse("2022-05-29"));
        this.requestForAllAvailableHotelService.setToDate(LocalDate.parse("2023-01-12"));
        this.requestForAllAvailableHotelService.setNumberOfAdults(90);


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

        AvailableHotelResponseDTO availableHotelResponseDTOFromBest = new AvailableHotelResponseDTO();
        availableHotelResponseDTOFromBest.setProviderName(bestHotel1.getProviderName());
        availableHotelResponseDTOFromBest.setHotelName(bestHotel1.getHotelName());
        availableHotelResponseDTOFromBest.setAmenities(bestHotel1.getRoomAmenities().split(","));
        availableHotelResponseDTOFromBest.setPrice(bestHotel1.getHotelFare());

        AvailableHotelResponseDTO availableHotelResponseDTOFromCrazy = new AvailableHotelResponseDTO();
        availableHotelResponseDTOFromCrazy.setPrice(crazyHotel1.getPrice());
        availableHotelResponseDTOFromCrazy.setHotelName(crazyHotel1.getHotelName());
        availableHotelResponseDTOFromCrazy.setAmenities(crazyHotel1.getAmenities());
        availableHotelResponseDTOFromCrazy.setProviderName(crazyHotel1.getProviderName());
        AvailableHotelResponse availableHotelResponse = new AvailableHotelResponse();
        availableHotelResponse.setResponse(Arrays.asList());


        when(availableHotelService.getAvailableHotels(requestForAllAvailableHotelService)).thenReturn(new ResponseEntity<>(availableHotelResponse, HttpStatus.OK));

        //when
        ResponseEntity<ResponseDTO> responseEntity = availableHotelController.availableHotel(this.requestForAllAvailableHotelService);

        assertTrue(responseEntity.getBody().getResponse().size() == 0);


    }



    @Test
    public void ShouldThrowException() {
        //given
        this.requestForAllAvailableHotelService.setCity("aomm");
        this.requestForAllAvailableHotelService.setFromDate(LocalDate.parse("2022-05-29"));
        this.requestForAllAvailableHotelService.setToDate(LocalDate.parse("2023-01-12"));
        this.requestForAllAvailableHotelService.setNumberOfAdults(90);


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

        AvailableHotelResponseDTO availableHotelResponseDTOFromBest = new AvailableHotelResponseDTO();
        availableHotelResponseDTOFromBest.setProviderName(bestHotel1.getProviderName());
        availableHotelResponseDTOFromBest.setHotelName(bestHotel1.getHotelName());
        availableHotelResponseDTOFromBest.setAmenities(bestHotel1.getRoomAmenities().split(","));
        availableHotelResponseDTOFromBest.setPrice(bestHotel1.getHotelFare());

        AvailableHotelResponseDTO availableHotelResponseDTOFromCrazy = new AvailableHotelResponseDTO();
        availableHotelResponseDTOFromCrazy.setPrice(crazyHotel1.getPrice());
        availableHotelResponseDTOFromCrazy.setHotelName(crazyHotel1.getHotelName());
        availableHotelResponseDTOFromCrazy.setAmenities(crazyHotel1.getAmenities());
        availableHotelResponseDTOFromCrazy.setProviderName(crazyHotel1.getProviderName());
        AvailableHotelResponse availableHotelResponse = new AvailableHotelResponse();
        availableHotelResponse.setResponse(Arrays.asList());


        when(availableHotelService.getAvailableHotels(requestForAllAvailableHotelService)).thenReturn(new ResponseEntity<>(availableHotelResponse, HttpStatus.OK));

        //when
        Executable ex = () -> availableHotelController.availableHotel(this.requestForAllAvailableHotelService);

        assertThrows(BaseSearchRequestException.class, ex);


    }






}