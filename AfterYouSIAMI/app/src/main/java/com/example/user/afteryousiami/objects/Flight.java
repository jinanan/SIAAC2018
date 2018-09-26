package com.example.user.afteryousiami.objects;

import java.util.Date;

public class Flight {

    private String origin;
    private String destination;
    private String flightNo;
    private Date departureDate;

    public Flight(String origin, String destination, String flightNo, Date departureDate) {
        this.origin = origin;
        this.destination = destination;
        this.flightNo = flightNo;
        this.departureDate = departureDate;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", flightNo='" + flightNo + '\'' +
                ", departureDate=" + departureDate +
                '}';
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Date getdepartureDate() {
        return departureDate;
    }

    public void setdepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
}
