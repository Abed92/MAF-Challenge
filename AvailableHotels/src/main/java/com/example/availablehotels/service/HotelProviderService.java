package com.example.availablehotels.service;

import com.example.availablehotels.dto.ResponseDTO;
import com.example.availablehotels.webservice.base.BaseSearchRequest;
import org.springframework.http.ResponseEntity;

public interface HotelProviderService {

    ResponseEntity<ResponseDTO> getHotels(BaseSearchRequest request);

}
