package com.example.availablehotels.controller;

import com.example.availablehotels.exception.BaseSearchRequestException;
import com.example.availablehotels.exception.InvalidResponseException;
import com.example.availablehotels.exception.responses.InvalidSearchResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlingController {


    @ExceptionHandler(value = {InvalidResponseException.class})
    public ResponseEntity<InvalidSearchResponse> handleInvalidResponse(Exception ex) {

        InvalidSearchResponse invalidSearchResponse = new InvalidSearchResponse();
        invalidSearchResponse.setTimestamp(LocalDateTime.now());
        invalidSearchResponse.setError(ex.getMessage());
        invalidSearchResponse.setStatus(3);

        return new ResponseEntity<>(invalidSearchResponse, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value = {BaseSearchRequestException.class})
    public ResponseEntity<InvalidSearchResponse> handleBaseSearchRequest(Exception ex) {

        InvalidSearchResponse invalidSearchResponse = new InvalidSearchResponse();
        invalidSearchResponse.setTimestamp(LocalDateTime.now());
        invalidSearchResponse.setError(ex.getMessage());
        invalidSearchResponse.setStatus(1);

        return new ResponseEntity<>(invalidSearchResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = {InvalidFormatException.class})
    public ResponseEntity<InvalidSearchResponse> handleInvalidNumberParsing(Exception ex, WebRequest request) {

        System.out.println(request);
        InvalidSearchResponse invalidSearchResponse = new InvalidSearchResponse();
        invalidSearchResponse.setTimestamp(LocalDateTime.now());
        invalidSearchResponse.setError(ex.getMessage());
        invalidSearchResponse.setStatus(1);

        return new ResponseEntity<>(invalidSearchResponse, HttpStatus.BAD_REQUEST);

    }
}
