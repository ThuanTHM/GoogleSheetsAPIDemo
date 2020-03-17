/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.training.apidemo.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;

/**
 * @author FB-001
 */
@Entity
@Table(name = "seat", schema = "dbexample")
public class Seat {

    private static final long serialVersionUID = 1L;

    private Long id;
    private int seatRank = 4;
    //available seat
    private int availableNum = 0;
    //total seat
    //todo dumming for missing business's feature
    private int totalNum = 0;//info of this field maybe moved into another entity storing Plane's info as soon as posible    
    private BigDecimal NETPrice = BigDecimal.ZERO;
    private BigDecimal adultPrice = BigDecimal.ZERO;//temporarily input by user
    private BigDecimal childPrice = BigDecimal.ZERO;//temporarily input by user
    private BigDecimal infantPrice = BigDecimal.ZERO;//temporarily input by user
    //    price for 3 different age ranks depends on these infomations and based on algorithm which may be cover as soon as possibledeparture:
    // -NET seat price
    // -system & admin fee
    // -domestic passenger service charge depend on international/ national flight
    // -domestic itineries: (including VAT 10%) for adult depending on airport. Children from 2 to under 12 years pay 50% applicable adult rate. Infants under 2 years without seats are exempt.
    // (VAT: regularly by 10% of NET seat price + dps)
    private Flight flight = new Flight();
    private Collection<Ticket> goTripTickets = new ArrayList<>();
    private Collection<Ticket> returnTripTickets = new ArrayList<>();

    public Seat(Flight flight) {
        this.flight = flight;
    }

    public Seat() {
    }

    public Seat(int seatRank, int totalNum, BigDecimal NETPrice, BigDecimal adultPrice, BigDecimal childPrice, BigDecimal infantPrice) {
        this.seatRank = seatRank;
        this.totalNum = totalNum;
        this.NETPrice = NETPrice;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
        this.infantPrice = infantPrice;
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

    //seat rank
    @Basic
    @Column(name = "seat_rank", nullable = true)
    public int getSeatRank() {
        return seatRank;
    }

    public void setSeatRank(int seatRank) {
        this.seatRank = seatRank;
    }

    //number of available seats (= total - sold out)
    @Basic
    @Column(name = "available_num", nullable = true)
    public int getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(int availableNum) {
        this.availableNum = availableNum;
    }

    //total seats
    @Basic
    @Column(name = "total_num", nullable = true)
    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalSeat) {
        this.totalNum = totalSeat;
    }

    //NET    
    @Basic
    @Column(name = "net_price", nullable = true)
    public BigDecimal getNETPrice() {
        return NETPrice;
    }

    public void setNETPrice(BigDecimal NETPrice) {
        this.NETPrice = NETPrice;
    }

    //adult price
    @Basic
    @Column(name = "adult_price", nullable = true)
    public BigDecimal getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(BigDecimal adultPrice) {
        this.adultPrice = adultPrice;
    }

    //child price
    @Basic
    @Column(name = "child_price", nullable = true)
    public BigDecimal getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(BigDecimal childPrice) {
        this.childPrice = childPrice;
    }

    //    infant price
    @Basic
    @Column(name = "infant_price", nullable = true)
    public BigDecimal getInfantPrice() {
        return infantPrice;
    }

    public void setInfantPrice(BigDecimal infantPrice) {
        this.infantPrice = infantPrice;
    }

    @OneToMany(mappedBy = "goTripSeat", fetch = FetchType.LAZY)
    public Collection<Ticket> getGoTripTickets() {
        return goTripTickets;
    }

    public void setGoTripTickets(Collection<Ticket> goTripTickets) {
        this.goTripTickets = goTripTickets;
    }

    @OneToMany(mappedBy = "returnTripSeat", fetch = FetchType.LAZY)
    public Collection<Ticket> getReturnTripTickets() {
        return returnTripTickets;
    }

    public void setReturnTripTickets(Collection<Ticket> returnTripTickets) {
        this.returnTripTickets = returnTripTickets;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", referencedColumnName = "id", nullable = false)
    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

}
