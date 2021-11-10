package com.example.availablehotels.dao;

import com.example.availablehotels.webservice.base.BaseSearchRequest;

import java.util.List;

public interface HotelProviderDao<T> {
    List<T> getHotels(BaseSearchRequest request);
}
