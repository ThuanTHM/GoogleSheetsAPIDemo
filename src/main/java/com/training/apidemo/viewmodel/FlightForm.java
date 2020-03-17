package com.training.apidemo.viewmodel;

import com.training.apidemo.entity.Airport;
import com.fasterxml.jackson.annotation.JsonView;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;

public class FlightForm {
    private String departureDatetimeLocalString;
    private String arrivalDatetimeLocalString;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private int totalSeat;
    private int availableSeat;
    private BigDecimal adultSeatPrice;//temporarily input by user //todo
    private BigDecimal childSeatPrice;//temporarily input by user //todo
    private BigDecimal infantSeatPrice;//temporarily input by user //todo

    public String getDepartureDatetimeLocalString() {
        return departureDatetimeLocalString;
    }

    public void setDepartureDatetimeLocalString(String departureDate) {
        this.departureDatetimeLocalString = departureDate;
    }

    public String getArrivalDatetimeLocalString() {
        return arrivalDatetimeLocalString;
    }

    public void setArrivalDatetimeLocalString(String arrivalDate) {
        this.arrivalDatetimeLocalString = arrivalDate;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public int getTotalSeat() {
        return totalSeat;
    }

    public void setTotalSeat(int totalSeat) {
        this.totalSeat = totalSeat;
    }

    public int getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(int availableSeat) {
        this.availableSeat = availableSeat;
    }

    public BigDecimal getAdultSeatPrice() {
        return adultSeatPrice;
    }

    public void setAdultSeatPrice(String adultSeatPrice) {
        this.adultSeatPrice = new BigDecimal(adultSeatPrice);
    }

    public BigDecimal getChildSeatPrice() {
        return childSeatPrice;
    }

    public void setChildSeatPrice(String childSeatPrice) {
        this.childSeatPrice = new BigDecimal(childSeatPrice);
    }

    public BigDecimal getInfantSeatPrice() {
        return infantSeatPrice;
    }

    public void setInfantSeatPrice(String infantSeatPrice) {
        this.infantSeatPrice = new BigDecimal(infantSeatPrice);
    }
}
