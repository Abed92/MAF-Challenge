package com.example.availablehotels.model;

import java.time.Instant;

public class CrazyHotel {

    private String hotelName;
    private String providerName;
    private String rate;
    private Double price;
    private String[] amenities;
    private Instant fromDate;
    private Instant toDate;
    private String city ;
    private Integer numberOfAdults;
    private Double roomDiscount;


    private CrazyHotel(CrazyHotelBuilder builder)
    {
        this.amenities =builder.amenities;
        this.city = builder.city;
        this.numberOfAdults = builder.numberOfAdults;
        this.fromDate = builder.fromDate;
        this.toDate = builder.toDate;
        this.price = builder.price;
        this.roomDiscount = builder.roomDiscount;
        this.rate = builder.rate;
        this.hotelName= builder.hotelName ;
        this.providerName = builder.providerName;
    }


    public String getProviderName() {
        return providerName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getRate() {
        return rate;
    }

    public Double getPrice() {
        return price;
    }

    public String[] getAmenities() {
        return amenities;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public String getCity() {
        return city;
    }

    public Integer getNumberOfAdults() {
        return numberOfAdults;
    }

    public Double getRoomDiscount() {
        return roomDiscount;
    }

    public static class CrazyHotelBuilder
    {
        private String hotelName;
        private String rate;
        private Double price;
        private String[] amenities;
        private Instant fromDate;
        private Instant toDate;
        private String city ;
        private Integer numberOfAdults;
        private Double roomDiscount;
        private String providerName;




        public CrazyHotelBuilder withProviderName(String providerName)
        {
            this.providerName = providerName;
            return this;
        }



        public CrazyHotelBuilder withHotelName(String hotelName)
        {
            this.hotelName = hotelName;
            return this;
        }

        public CrazyHotelBuilder withRate(String rate)
        {
            this.rate = rate;
            return this;
        }

        public  CrazyHotelBuilder withPrice(Double price)
        {
            this.price = price;
            return this;
        }


        public CrazyHotelBuilder withRoomAmenities( String[] amenities)
        {
            this.amenities = amenities;
            return this;
        }


        public CrazyHotelBuilder withFromDate(Instant fromDate)
        {
            this.fromDate = fromDate;
            return this;
        }


        public CrazyHotelBuilder withToDate(Instant toDate)
        {
            this.toDate = toDate;
            return this;
        }

        public  CrazyHotelBuilder withCity(String city)
        {
            this.city = city;
            return this;
        }


        public CrazyHotelBuilder withNumberOfAdults(Integer numberOfAdults)
        {
            this.numberOfAdults = numberOfAdults;
            return this;
        }


        public   CrazyHotelBuilder withRoomDiscount(Double roomDiscount)
        {
            this.roomDiscount = roomDiscount;
            return this;
        }

        public CrazyHotel build()
        {
            return new CrazyHotel(this);
        }

    }
}
