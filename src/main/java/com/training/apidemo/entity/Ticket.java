/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.training.apidemo.entity;

//<editor-fold desc="Import">
import java.math.BigDecimal;
import javax.persistence.*;
//</editor-fold>

/**
 * @author FB-001
 */
@Entity
public class Ticket {
    private static final long serialVersionUID = 1L;
    private Long id;
    private boolean roundticket;//return trip or not (one-way trip)
    private int ageRank;//value: 0 - adult > 11; 1 - child > 2 < 11; 2 - infant < 2
    private String firstname;
    private String lastname;
    private BigDecimal goTripPrice;
    private BigDecimal returnTripPrice;
    private Seat goTripSeat;
    private Seat returnTripSeat;
    private Order order;
    //other info such as membership, luggage extras blah blah luc bat tong tam

    public Ticket() {
    }

    public Ticket(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Ticket(boolean roundticket, int ageRank, BigDecimal goTripPrice, BigDecimal returnTripPrice, Seat goTripSeat, Seat returnTripSeat) {
        this.roundticket = roundticket;
        this.ageRank = ageRank;
        this.goTripPrice = goTripPrice;
        this.returnTripPrice = returnTripPrice;
        this.goTripSeat = goTripSeat;
        this.returnTripSeat = returnTripSeat;
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
    @Column(name = "round_ticket", nullable = true)
    public boolean isRoundticket() {
        return roundticket;
    }

    public void setRoundticket(boolean roundticket) {
        this.roundticket = roundticket;
    }

    @Basic
    @Column(name = "age_rank", nullable = true)
    public int getAgeRank() {
        return ageRank;
    }

    public void setAgeRank(int ageRank) {
        this.ageRank = ageRank;
    }

    @Basic
    @Column(name = "firstname", nullable = true)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname", nullable = true)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "go_trip_price", nullable = true)
    public BigDecimal getGoTripPrice() {
        return goTripPrice;
    }

    public void setGoTripPrice(BigDecimal goTripPrice) {
        this.goTripPrice = goTripPrice;
    }

    @Basic
    @Column(name = "return_trip_price", nullable = true)
    public BigDecimal getReturnTripPrice() {
        return returnTripPrice;
    }

    public void setReturnTripPrice(BigDecimal returnTripPrice) {
        this.returnTripPrice = returnTripPrice;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "go_trip_seat_id", referencedColumnName = "id", nullable = false)
    public Seat getGoTripSeat() {
        return goTripSeat;
    }

    public void setGoTripSeat(Seat goTripSeat) {
        this.goTripSeat = goTripSeat;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_trip_seat_id", referencedColumnName = "id", nullable = false)
    public Seat getReturnTripSeat() {
        return returnTripSeat;
    }

    public void setReturnTripSeat(Seat returnTripSeat) {
        this.returnTripSeat = returnTripSeat;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
