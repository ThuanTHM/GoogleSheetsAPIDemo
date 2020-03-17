package com.training.apidemo.viewmodel;

import com.training.apidemo.entity.Airport;
import com.fasterxml.jackson.annotation.JsonView;

import java.sql.Timestamp;

public class OrderForm {
    private int isReturnTrip;
    private int adultPassengerNum;
    private int childrenPassengerNum;
    private int infantPassengerNum;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private Airport departureAirport;

    public int getIsReturnTrip() {
        return isReturnTrip;
    }

    public void setIsReturnTrip(int isReturnTrip) {
        this.isReturnTrip = isReturnTrip;
    }

    public int getAdultPassengerNum() {
        return adultPassengerNum;
    }

    public void setAdultPassengerNum(int adultPassengerNum) {
        this.adultPassengerNum = adultPassengerNum;
    }

    public int getChildrenPassengerNum() {
        return childrenPassengerNum;
    }

    public void setChildrenPassengerNum(int childrenPassengerNum) {
        this.childrenPassengerNum = childrenPassengerNum;
    }

    public int getInfantPassengerNum() {
        return infantPassengerNum;
    }

    public void setInfantPassengerNum(int infantPassengerNum) {
        this.infantPassengerNum = infantPassengerNum;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
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

    private Airport arrivalAirport;
}
