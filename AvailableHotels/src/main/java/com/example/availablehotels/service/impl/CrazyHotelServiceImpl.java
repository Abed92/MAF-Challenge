package com.example.availablehotels.service.impl;

import com.example.availablehotels.dao.HotelProviderDao;
import com.example.availablehotels.dto.CrazyHotelResponseDTO;
import com.example.availablehotels.dto.ResponseDTO;
import com.example.availablehotels.exception.ServerProcessingException;
import com.example.availablehotels.model.CrazyHotel;
import com.example.availablehotels.service.HotelProviderService;
import com.example.availablehotels.webservice.base.BaseSearchRequest;
import com.example.availablehotels.webservice.providers.crazyhotel.CrazyHotelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Qualifier("CrazyHotelServiceImpl")
@Service
public class CrazyHotelServiceImpl implements HotelProviderService {



    HotelProviderDao crazyHotelProviderDao;

    public CrazyHotelServiceImpl(@Qualifier("CrazyHotelProviderDaoImpl") HotelProviderDao crazyHotelProviderDao)
    {
        this.crazyHotelProviderDao = crazyHotelProviderDao;
    }
    @Override
    public ResponseEntity<ResponseDTO> getHotels(BaseSearchRequest request) {

        try {
            request.isValid();
            List<CrazyHotel> hotels = crazyHotelProviderDao.getHotels(request);
            List<CrazyHotelResponseDTO> crazyHotelResponseDTO = BuildResponseDTO(hotels);
            ResponseDTO<CrazyHotelResponseDTO> crazyHotelResponse = new CrazyHotelResponse();
            crazyHotelResponse.setResponse(crazyHotelResponseDTO);
            return new ResponseEntity<>(crazyHotelResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new ServerProcessingException("Invalid Response");
        }


    }

    private List<CrazyHotelResponseDTO> BuildResponseDTO(List<CrazyHotel> hotels) {

        try {

            List<CrazyHotelResponseDTO> crazyHotelResponseDTOS = hotels.stream().map(item -> {
                CrazyHotelResponseDTO crazyHotelResponseDTO = new CrazyHotelResponseDTO();
                crazyHotelResponseDTO.setHotelName(item.getHotelName());
                crazyHotelResponseDTO.setAmenities(item.getAmenities());
                crazyHotelResponseDTO.setPrice(item.getPrice());
                crazyHotelResponseDTO.setRate(item.getRate());
                if (item.getRoomDiscount() == null) {
                    crazyHotelResponseDTO.setDiscount(0.0);
                } else {
                    crazyHotelResponseDTO.setDiscount(item.getRoomDiscount());
                }
                return crazyHotelResponseDTO;
            }).collect(Collectors.toList());

            return crazyHotelResponseDTOS;
        } catch (Exception e) {
            throw new ServerProcessingException("Invalid Response");
        }

    }


}
