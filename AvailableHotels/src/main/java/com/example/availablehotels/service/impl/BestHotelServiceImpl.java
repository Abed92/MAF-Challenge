package com.example.availablehotels.service.impl;

import com.example.availablehotels.dao.HotelProviderDao;
import com.example.availablehotels.dto.BestHotelResponseDTO;
import com.example.availablehotels.dto.ResponseDTO;
import com.example.availablehotels.exception.ServerProcessingException;
import com.example.availablehotels.model.BestHotel;
import com.example.availablehotels.service.HotelProviderService;
import com.example.availablehotels.webservice.base.BaseSearchRequest;
import com.example.availablehotels.webservice.providers.besthotel.BestHotelResponse;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("BestHotelServiceImpl")
public class BestHotelServiceImpl implements HotelProviderService {



    HotelProviderDao bestHotelDao;

    public BestHotelServiceImpl (@Qualifier("BestHotelProviderDaoImpl") HotelProviderDao bestHotelDao)
    {
        this.bestHotelDao = bestHotelDao;
    }
    @Override
    public ResponseEntity<ResponseDTO> getHotels(BaseSearchRequest request) {

        try {
            request.isValid();
            List<BestHotel> hotels = bestHotelDao.getHotels(request);
            List<BestHotelResponseDTO> bestHotelResponseDTO = BuildResponseDTO(hotels);
            ResponseDTO<BestHotelResponseDTO> bestHotelResponse = new BestHotelResponse();
            bestHotelResponse.setResponse(bestHotelResponseDTO);
            return new ResponseEntity<>(bestHotelResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new ServerProcessingException("Invalid Response");
        }

    }

    private List<BestHotelResponseDTO> BuildResponseDTO(List<BestHotel> hotels) {


        try {
            List<BestHotelResponseDTO> bestHotelResponseDTOS = hotels.stream().map(item ->
            {
                BestHotelResponseDTO bestHotelResponseDTO = new BestHotelResponseDTO();
                bestHotelResponseDTO.setHotelFare(Precision.round(item.getHotelFare(), 2));
                bestHotelResponseDTO.setHotelName(item.getHotelName());
                bestHotelResponseDTO.setHotelRate(String.valueOf(item.getHotelRate()));
                bestHotelResponseDTO.setRoomAmenities(String.join(",", item.getRoomAmenities()));
                return bestHotelResponseDTO;
            }).collect(Collectors.toList());

            return bestHotelResponseDTOS;

        } catch (Exception e) {
            throw new ServerProcessingException("Invalid Response");
        }

    }


}
