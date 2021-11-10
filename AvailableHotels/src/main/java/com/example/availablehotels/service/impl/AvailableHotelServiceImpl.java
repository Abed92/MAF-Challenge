package com.example.availablehotels.service.impl;

import com.example.availablehotels.dao.HotelProviderDao;
import com.example.availablehotels.dto.AvailableHotelResponseDTO;
import com.example.availablehotels.dto.ResponseDTO;
import com.example.availablehotels.exception.ServerProcessingException;
import com.example.availablehotels.model.BestHotel;
import com.example.availablehotels.model.CrazyHotel;
import com.example.availablehotels.service.AvailableHotelService;
import com.example.availablehotels.webservice.availablehotel.AvailableHotelResponse;
import com.example.availablehotels.webservice.base.BaseSearchRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service

public class AvailableHotelServiceImpl implements AvailableHotelService {

    HotelProviderDao bestHotelDao;
    HotelProviderDao crazyHotelDao;

    public AvailableHotelServiceImpl(@Qualifier("BestHotelProviderDaoImpl")HotelProviderDao bestHotelDao, @Qualifier("CrazyHotelProviderDaoImpl") HotelProviderDao crazyHotelDao) {
        this.bestHotelDao = bestHotelDao;
        this.crazyHotelDao = crazyHotelDao;
    }

    @Override
    public ResponseEntity<ResponseDTO> getAvailableHotels(BaseSearchRequest request) {

        try {
            request.isValid();
            List<AvailableHotelResponseDTO> availableHotelResponseDTO = retrieveFromAllProviders(request);
            ResponseDTO<AvailableHotelResponseDTO> availableHotelResponse = new AvailableHotelResponse();
            availableHotelResponse.setResponse(availableHotelResponseDTO);
            return new ResponseEntity<>(availableHotelResponse, HttpStatus.OK);

        } catch (Exception e) {
            throw new ServerProcessingException("Invalid Response");
        }


    }

    private List<AvailableHotelResponseDTO> retrieveFromAllProviders(BaseSearchRequest request) {

        try {
            List<BestHotel> betsHotel = bestHotelDao.getHotels(request);
            List<CrazyHotel> crazyHotels = crazyHotelDao.getHotels(request);
            List<AvailableHotelResponseDTO> availableHotelResponseDTOS = mergeResults(betsHotel, crazyHotels);
            return availableHotelResponseDTOS;

        } catch (Exception e) {
            throw new ServerProcessingException("Invalid Response");
        }


    }

    private List<AvailableHotelResponseDTO> mergeResults(List<BestHotel> betsHotel, List<CrazyHotel> crazyHotels) {

        try {
            if (betsHotel.size() == 0 || crazyHotels.size() == 0)
                return new ArrayList<AvailableHotelResponseDTO>();
            List<BestHotel> sortedBestHotels = sortBestHotelByRate(betsHotel);
            List<CrazyHotel> sortedCrazyHotels1 = sortCrazyHotelByRate(crazyHotels);
            List<AvailableHotelResponseDTO> availableHotelResponseDTOS = buildAvailableHotelResponseDTO(sortedBestHotels, sortedCrazyHotels1);
            return availableHotelResponseDTOS;
        } catch (Exception e) {
            throw new ServerProcessingException("Invalid Response");
        }

    }

    private List<CrazyHotel> sortCrazyHotelByRate(List<CrazyHotel> crazyHotels) {
        Comparator<CrazyHotel> comparator = Comparator.comparing(e -> e.getRate().length());
        List<CrazyHotel> sortedList = crazyHotels.stream().sorted(comparator.reversed()).collect(Collectors.toList());
        return sortedList;
    }

    private List<BestHotel> sortBestHotelByRate(List<BestHotel> betsHotel) {
        Comparator<BestHotel> comparator = Comparator.comparing(e -> e.getHotelRate());
        List<BestHotel> sortedList = betsHotel.stream().sorted(comparator.reversed()).collect(Collectors.toList());
        return sortedList;

    }


    private List<AvailableHotelResponseDTO> buildAvailableHotelResponseDTO(List<BestHotel> castedBestModel, List<CrazyHotel> castedCrazyModel) {

        try {

            List<AvailableHotelResponseDTO> collectedFromBestHotel = castedBestModel.stream().map(item ->
            {
                AvailableHotelResponseDTO availableHotelResponseDTO = new AvailableHotelResponseDTO();
                availableHotelResponseDTO.setAmenities(item.getRoomAmenities().split(","));
                availableHotelResponseDTO.setHotelName(item.getHotelName());
                availableHotelResponseDTO.setPrice(item.getHotelFare());
                availableHotelResponseDTO.setProviderName(item.getProviderName());
                return availableHotelResponseDTO;
            }).collect(Collectors.toList());

            List<AvailableHotelResponseDTO> collectedFromCrazyHotel = castedCrazyModel.stream().map(item ->
            {
                AvailableHotelResponseDTO availableHotelResponseDTO = new AvailableHotelResponseDTO();
                availableHotelResponseDTO.setAmenities(item.getAmenities());
                availableHotelResponseDTO.setHotelName(item.getHotelName());
                availableHotelResponseDTO.setPrice(item.getPrice());
                availableHotelResponseDTO.setProviderName(item.getProviderName());
                return availableHotelResponseDTO;
            }).collect(Collectors.toList());

            List<AvailableHotelResponseDTO> availableHotelResponseDTOS = Stream.concat(collectedFromBestHotel.stream(), collectedFromCrazyHotel.stream()).collect(Collectors.toList());
            return availableHotelResponseDTOS;
        } catch (Exception e) {
            throw new ServerProcessingException("Invalid Response");
        }


    }

}
