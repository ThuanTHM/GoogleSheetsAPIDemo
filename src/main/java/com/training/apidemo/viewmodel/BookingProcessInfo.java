/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.training.apidemo.viewmodel;

//<editor-fold desc="Import">
import com.training.apidemo.entity.Seat;
import com.training.apidemo.entity.Ticket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
//</editor-fold>

/**
 *
 * @author FB-001
 */
public class BookingProcessInfo {

    private String departureLocation = "";
    private String arrivalLocation = "";
    private boolean roundticket = true;
    private int numOfAdult = 0;
    private int numOfChildren = 0;
    private int numOfInfant = 0;
    private Date departureDate = new Date();
    private Date returnDate = new Date();
    private Seat departingTrip = new Seat();//departing seat id
    private Seat returningTrip = new Seat();//returning seat id
    private String contactFirstName = "";
    private String contactLastName = "";
    private String contactPhoneNum = "";
    private String contactEmail = "";
    private String contactAddress = "";
    private Collection<Ticket> adultTickets = new ArrayList<>();
    private Collection<Ticket> childrenTickets = new ArrayList<>();
    private Collection<Ticket> infantTickets = new ArrayList<>();

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getContactPhoneNum() {
        return contactPhoneNum;
    }

    public void setContactPhoneNum(String contactPhoneNum) {
        this.contactPhoneNum = contactPhoneNum;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }
        
    public boolean isRoundticket() {
        return roundticket;
    }

    public void setRoundticket(boolean roundticket) {
        this.roundticket = roundticket;
    }

    public int getNumOfAdult() {
        return numOfAdult;
    }

    public void setNumOfAdult(int numOfAdult) {
        this.numOfAdult = numOfAdult;
    }

    public int getNumOfChildren() {
        return numOfChildren;
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
    }

    public int getNumOfInfant() {
        return numOfInfant;
    }

    public void setNumOfInfant(int numOfInfant) {
        this.numOfInfant = numOfInfant;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Seat getDepartingTrip() {
        return departingTrip;
    }

    public void setDepartingTrip(Seat departingTrip) {
        this.departingTrip = departingTrip;
    }

    public Seat getReturningTrip() {
        return returningTrip;
    }

    public void setReturningTrip(Seat returningTrip) {
        this.returningTrip = returningTrip;
    }

    public Collection<Ticket> getAdultTickets() {
        return adultTickets;
    }

    public void setAdultTickets(Collection<Ticket> adultTickets) {
        this.adultTickets = adultTickets;
    }

    public Collection<Ticket> getChildrenTickets() {
        return childrenTickets;
    }

    public void setChildrenTickets(Collection<Ticket> childrenTickets) {
        this.childrenTickets = childrenTickets;
    }

    public Collection<Ticket> getInfantTickets() {
        return infantTickets;
    }

    public void setInfantTickets(Collection<Ticket> infantTickets) {
        this.infantTickets = infantTickets;
    }

}
