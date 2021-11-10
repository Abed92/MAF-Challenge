package com.example.availablehotels.model;

import java.time.LocalDate;

public class BestHotel {

    private String hotelName;
    private Integer hotelRate;
    private Double hotelFare;
    private String roomAmenities;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String city;
    private Integer numberOfAdults;
    private String providerName;


    private BestHotel(BestHotelBuilder builder) {
        this.roomAmenities = builder.roomAmenities;
        this.city = builder.city;
        this.numberOfAdults = builder.numberOfAdults;
        this.fromDate = builder.fromDate;
        this.toDate = builder.toDate;
        this.hotelFare = builder.hotelFare;
        this.hotelRate = builder.hotelRate;
        this.hotelName = builder.hotelName;
        this.providerName = builder.providerName;
    }


    public String getProviderName() {
        return providerName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public Integer getHotelRate() {
        return hotelRate;
    }

    public Double getHotelFare() {
        return hotelFare;
    }

    public String getRoomAmenities() {
        return roomAmenities;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public String getCity() {
        return city;
    }

    public Integer getNumberOfAdults() {
        return numberOfAdults;
    }

    public static class BestHotelBuilder {
        private String hotelName;
        private Integer hotelRate;
        private Double hotelFare;
        private String roomAmenities;
        private LocalDate fromDate;
        private LocalDate toDate;
        private String city;
        private Integer numberOfAdults;
        private String providerName;


        public BestHotelBuilder withRoomAmenities(String roomAmenities) {
            this.roomAmenities = roomAmenities;
            return this;
        }


        public BestHotelBuilder withProviderName(String providerName) {
            this.providerName = providerName;
            return this;
        }


        public BestHotelBuilder withHotelName(String hotelName) {
            this.hotelName = hotelName;
            return this;
        }

        public BestHotelBuilder withRate(Integer rate) {
            this.hotelRate = rate;
            return this;
        }

        public BestHotelBuilder withPrice(Double hotelFare) {
            this.hotelFare = hotelFare;
            return this;
        }


        public BestHotelBuilder withFromDate(LocalDate fromDate) {
            this.fromDate = fromDate;
            return this;
        }


        public BestHotelBuilder withToDate(LocalDate toDate) {
            this.toDate = toDate;
            return this;
        }

        public BestHotelBuilder withCity(String city) {
            this.city = city;
            return this;
        }


        public BestHotelBuilder withNumberOfAdults(Integer numberOfAdults) {
            this.numberOfAdults = numberOfAdults;
            return this;
        }


        public BestHotel build() {
            return new BestHotel(this);
        }

    }
}
