package com.example.availablehotels.webservice.base;

import com.example.availablehotels.exception.InvalidCityException;
import com.example.availablehotels.exception.InvalidDurationException;
import com.example.availablehotels.exception.InvalidFromDateException;
import com.example.availablehotels.exception.InvalidNumberOfAdults;

import java.time.LocalDate;

public abstract class BaseSearchRequest {

    private LocalDate fromDate;
    private LocalDate toDate;
    private String city;
    private Integer numberOfAdults;


    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(Integer numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public boolean isValid() {
        boolean validCity = validateCity(this.getCity());
        boolean validateFromDate = validateFromDate(this.getFromDate());
        boolean validateDuration = validateDuration(this.getFromDate(), this.getToDate());
        boolean validateNumberOfAdults = validateNumberOfAdults(this.getNumberOfAdults());
        if (validCity && validateFromDate && validateDuration && validateNumberOfAdults)
            return true;
        return false;
    }

    private boolean validateNumberOfAdults(Integer numberOfAdults) {
        if (numberOfAdults < 0)
            throw new InvalidNumberOfAdults("Number of adults can not be less than 0.");

        return true;

    }

    private boolean validateDuration(LocalDate fromDate, LocalDate toDate) {

        if (toDate == null)
            throw new InvalidDurationException("To date can not be empty or null.");

        if (toDate.isBefore(fromDate))
            throw new InvalidDurationException("Invalid Duration (From Date must be before To Date.)");


        return true;
    }

    private boolean validateFromDate(LocalDate fromDate) {
        if (fromDate == null)
            throw new InvalidFromDateException("From date code can not be empty or null.");
        if (fromDate.isBefore(LocalDate.now()))
            throw new InvalidFromDateException("From date can not be a previous date.");

        return true;
    }


    private boolean validateCity(String city) {

        if (city == null)
            throw new InvalidCityException("City code can not be empty or null.");
        if (city.isEmpty())
            throw new InvalidCityException("City code can not be empty or null.");
        if (city.length() > 3 || city.length() < 3)
            throw new InvalidCityException("City code can not be more than 3 letters.Please use IATA code.");

        return true;

    }
}
