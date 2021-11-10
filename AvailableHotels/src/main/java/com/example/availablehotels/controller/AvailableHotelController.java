package com.example.availablehotels.controller;

import com.example.availablehotels.dto.ResponseDTO;
import com.example.availablehotels.exception.InvalidResponseException;
import com.example.availablehotels.service.AvailableHotelService;
import com.example.availablehotels.service.HotelProviderService;
import com.example.availablehotels.webservice.availablehotel.AvailableHotelRequest;
import com.example.availablehotels.webservice.base.BaseSearchRequest;
import com.example.availablehotels.webservice.providers.besthotel.BestHotelRequest;
import com.example.availablehotels.webservice.providers.crazyhotel.CrazyHotelsRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(value = "Hotel Rest Controller", description = "REST API for User")
public class AvailableHotelController {


    HotelProviderService crazyHotelHotelProviderService;
    HotelProviderService bestHotelHotelProviderService;
    AvailableHotelService availableHotelService;

    public AvailableHotelController(@Qualifier("CrazyHotelServiceImpl") HotelProviderService crazyHotelHotelProviderService,
                                    @Qualifier("BestHotelServiceImpl")
                                    HotelProviderService bestHotelHotelProviderService, AvailableHotelService availableHotelService
    ) {
        this.crazyHotelHotelProviderService = crazyHotelHotelProviderService;
        this.bestHotelHotelProviderService = bestHotelHotelProviderService;
        this.availableHotelService = availableHotelService;
    }

    @ApiOperation(value = "Get User by User Id ", response = Object.class, tags = "getAvailableHotels")
    @PostMapping("/hotels/available")
    public ResponseEntity<ResponseDTO> availableHotel(@RequestBody AvailableHotelRequest request) {
        request.isValid();
        ResponseEntity<ResponseDTO> hotels = availableHotelService.getAvailableHotels(request);

        if (hotels != null)
            return new ResponseEntity<>(hotels.getBody(), HttpStatus.OK);

        throw new InvalidResponseException("Invalid Response");

    }


    @ApiOperation(value = "Get hotels from best hotel provider", response = Object.class, tags = "getBestProvider")
    @PostMapping("/hotels/best")
    public ResponseEntity<ResponseDTO> bestHotel(@RequestBody BestHotelRequest request) {
        request.isValid();
        ResponseEntity<ResponseDTO> responseEntity = bestHotelHotelProviderService.getHotels(request);
        if (responseEntity != null)
            return responseEntity;

        throw new InvalidResponseException("Invalid Response");
    }

    @ApiOperation(value = "Get hotels from crazy hotel provider", response = Object.class, tags = "getCrazyProvider")
    @PostMapping("/hotels/crazy")
    public ResponseEntity<ResponseDTO> crazyHotel(@RequestBody CrazyHotelsRequest request) {
        request.isValid();
        ResponseEntity<ResponseDTO> responseEntity = crazyHotelHotelProviderService.getHotels(request);
        if (responseEntity != null)
            return responseEntity;
        throw new InvalidResponseException("Invalid Response");
    }

}

