package com.example.availablehotels.dto;

public class BestHotelResponseDTO {

    String hotelName;
    String hotelRate;
    Double hotelFare;
    String roomAmenities;


    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelRate() {
        return hotelRate;
    }

    public void setHotelRate(String hotelRate) {
        this.hotelRate = hotelRate;
    }

    public Double getHotelFare() {
        return hotelFare;
    }

    public void setHotelFare(Double hotelFare) {
        this.hotelFare = hotelFare;
    }

    public String getRoomAmenities() {
        return roomAmenities;
    }

    public void setRoomAmenities(String roomAmenities) {
        this.roomAmenities = roomAmenities;
    }
}
