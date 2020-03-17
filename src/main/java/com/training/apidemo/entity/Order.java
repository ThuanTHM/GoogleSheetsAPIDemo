/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.training.apidemo.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author FB-001
 */
@Entity
@Table(name = "orderinf")
public class Order {

    private static final long serialVersionUID = 1L;

    private Long id;//order's id
    private Date orderTime = new Date();//time created of order, maybe at last modified before order being processed
    private boolean roundticket = true;//return trip or not (one-way trip)
    private String contactFirstName = "";
    private String contactLastName = "";
    private String contactPhoneNum;
    private String contactEmail = "";
    private String contactAddress = "";

    private Collection<Ticket> tickets = new ArrayList<>();

    public Order(Date orderTime, boolean roundticket, String contactFirstName, String contactLastName, String contactPhoneNum, String contactEmail, String contactAddress) {
        this.orderTime = orderTime;
        this.roundticket = roundticket;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.contactPhoneNum = contactPhoneNum;
        this.contactEmail = contactEmail;
        this.contactAddress = contactAddress;
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

    //todo set nullable = false
    @Basic
    @Column(name = "order_time", nullable = true)
    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    //todo set nullable = false
    @Basic
    @Column(name = "roundticket", nullable = true)
    public boolean isRoundticket() {
        return roundticket;
    }

    public void setRoundticket(boolean roundticket) {
        this.roundticket = roundticket;
    }

    //todo set nullable = false
    @Basic
    @Column(name = "contact_first_name", nullable = true)
    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    @Basic
    @Column(name = "contact_last_name", nullable = true)
    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    //todo set nullable = false
    @Basic
    @Column(name = "contact_phone_num", nullable = true)
    public String getContactPhoneNum() {
        return contactPhoneNum;
    }

    public void setContactPhoneNum(String contactPhoneNum) {
        this.contactPhoneNum = contactPhoneNum;
    }

    //todo set nullable = false
    @Basic
    @Column(name = "contact_email", nullable = true)
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    //todo set nullable = false
    @Basic
    @Column(name = "contact_address", nullable = true)
    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }
}
