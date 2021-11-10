package com.example.availablehotels.exception;

public abstract class BaseSearchRequestException extends RuntimeException {


    public BaseSearchRequestException(String message) {
        super(message);
    }

}
