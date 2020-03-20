/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.training.apidemo.entity;

import java.util.Date;
import java.util.Collection;
import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author FB-001
 */
@Entity
@Table(name = "flight", schema = "dbexample")
public class Flight {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private Date departureTime;
    private Date arrivalTime;
    private Airport departureAirport;
    private Airport arrivalAirport;

    //    price for 3 different age ranks depends on these infomations and based on algorithm which may be cover as soon as possibledeparture:
    // -NET seat price
    // -system & admin fee
    // -domestic passenger service charge depend on international/ national flight
    // -domestic itineries: (including VAT 10%) for adult depending on airport. Children from 2 to under 12 years pay 50% applicable adult rate. Infants under 2 years without seats are exempt.
    // (VAT: regularly by 10% of NET seat price + dps)
    private Collection<Seat> seats;

    public Flight(Long id, String code, Date departureTime, Date arrivalTime, Airport departureAirport, Airport arrivalAirport, Collection<Seat> seats) {
        this.id = id;
        this.code = code;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.seats = seats;
    }

    public Flight(String code, Date departureTime, Date arrivalTime, Airport departureAirport, Airport arrivalAirport, Collection<Seat> seats) {
        this.code = code;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.seats = seats;
    }

    public Flight() {

    }

    public Flight(Date departureTime, Date arrivalTime) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code", nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "departure_time", nullable = false)
    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    @Basic
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "arrival_time", nullable = false)
    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_airport_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_airport_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY)
    public Collection<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Collection<Seat> seats) {
        this.seats = seats;
    }
}
