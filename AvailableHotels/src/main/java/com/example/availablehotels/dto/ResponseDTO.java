package com.example.availablehotels.dto;

import java.util.ArrayList;
import java.util.List;

public abstract class ResponseDTO<T> {

    List<T> response = new ArrayList<>();

    public List<T> getResponse() {
        return response;
    }

    public void setResponse(List<T> responseType) {
        this.response = responseType;
    }
}
