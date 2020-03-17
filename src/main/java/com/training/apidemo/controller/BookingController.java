package com.training.apidemo.controller;

//<editor-fold desc="Import">

import com.training.apidemo.entity.Order;
import com.training.apidemo.entity.Ticket;
import com.training.apidemo.exception.RecordNotFoundException;
import com.training.apidemo.service.AirportService;
import com.training.apidemo.service.FlightService;
import com.training.apidemo.service.OrderService;
import com.training.apidemo.service.SeatService;
import com.training.apidemo.service.TicketService;
import com.training.apidemo.viewmodel.BookingProcessInfo;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
//</editor-fold>

@Controller
@RequestMapping(path = {"booking", ""})
public class BookingController {

    @Autowired
    private FlightService flightService;
    @Autowired
    private AirportService airportService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TicketService ticketService;

    /**
     * @param request
     * @return booking_search for flights with bookingInfo model
     */
    @RequestMapping(path = {"/search", ""})
    public ModelAndView getAll(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("booking_search");
        model.addObject("bookingInfo", new BookingProcessInfo());
        model.addObject("locations", airportService.findDistinctLocations());
        return model;
    }

    /**
     * @param bookingInfo
     * @return
     */
    @RequestMapping(path = "/select/flight", method = RequestMethod.POST)
    public ModelAndView searchFlights(@ModelAttribute BookingProcessInfo bookingInfo) {
        ModelAndView model = new ModelAndView("booking_select_flight");
        model.addObject("departingSeats", seatService.findSuitableDepartingSlots(bookingInfo.getDepartureLocation(), bookingInfo.getArrivalLocation(), bookingInfo.getDepartureDate()));
        if (bookingInfo.isRoundticket()) {
            model.addObject("returningSeats", seatService.findSuitableReturningSlots(bookingInfo.getArrivalLocation(), bookingInfo.getDepartureLocation(), bookingInfo.getReturnDate()));
        }
        model.addObject("bookingInfo", bookingInfo);
        return model;
    }

    /**
     * @param bookingInfo
     * @return
     * @throws RecordNotFoundException
     */
    @RequestMapping(path = "/order/contact", method = RequestMethod.POST)
    public ModelAndView selectFlights(@ModelAttribute BookingProcessInfo bookingInfo) throws RecordNotFoundException {
        ModelAndView model = new ModelAndView("booking_ticket_filling");
        int i;
        bookingInfo.setDepartingTrip(seatService.findById(bookingInfo.getDepartingTrip().getId()));
        bookingInfo.setReturningTrip(seatService.findById(bookingInfo.getReturningTrip().getId()));
        for (i = 0; i < bookingInfo.getNumOfAdult(); i++) {//ageRank  = 0 (adult)
            bookingInfo.getAdultTickets().add(
                    new Ticket(
                            bookingInfo.isRoundticket(),
                            0,
                            //                            seatService.findById(bookingInfo.getDepartingTrip()).getAdultPrice(),
                            bookingInfo.getDepartingTrip().getAdultPrice(),
                            //                            (bookingInfo.order.isRoundticket() ? seatService.findById(bookingInfo.getReturningTrip()).getAdultPrice() : BigDecimal.ZERO
                            (bookingInfo.isRoundticket() ? bookingInfo.getReturningTrip().getAdultPrice() : BigDecimal.ZERO),
                            //                            seatService.findById(bookingInfo.getDepartingTrip())
                            bookingInfo.getDepartingTrip(),
                            //                            seatService.findById(bookingInfo.getReturningTrip())
                            bookingInfo.getReturningTrip()
                    ));
        }
        for (i = 0; i < bookingInfo.getNumOfChildren(); i++) {//ageRank  = 1 (children) 
            bookingInfo.getChildrenTickets().add(
                    new Ticket(
                            bookingInfo.isRoundticket(),
                            1,
                            //                            seatService.findById(bookingInfo.getDepartingTrip()).getChildPrice(),
                            bookingInfo.getDepartingTrip().getChildPrice(),
                            //                            (bookingInfo.order.isRoundticket() ? seatService.findById(bookingInfo.getReturningTrip()).getChildPrice() : BigDecimal.ZERO),
                            (bookingInfo.isRoundticket() ? bookingInfo.getReturningTrip().getChildPrice() : BigDecimal.ZERO),
                            //                            seatService.findById(bookingInfo.getDepartingTrip()),
                            bookingInfo.getDepartingTrip(),
                            //                            seatService.findById(bookingInfo.getReturningTrip())
                            bookingInfo.getReturningTrip()
                    ));
        }
        for (i = 0; i < bookingInfo.getNumOfInfant(); i++) {//ageRank  = 0 (infant) 
            bookingInfo.getInfantTickets().add(
                    new Ticket(
                            bookingInfo.isRoundticket(),
                            2,
                            //                            seatService.findById(bookingInfo.getDepartingTrip()).getInfantPrice(),
                            bookingInfo.getDepartingTrip().getInfantPrice(),
                            //                            (bookingInfo.order.isRoundticket() ? seatService.findById(bookingInfo.getReturningTrip()).getInfantPrice() : BigDecimal.ZERO),
                            (bookingInfo.isRoundticket() ? bookingInfo.getReturningTrip().getInfantPrice() : BigDecimal.ZERO),
                            //                            seatService.findById(bookingInfo.getDepartingTrip()),
                            seatService.findById(bookingInfo.getDepartingTrip().getId()),
                            //                            seatService.findById(bookingInfo.getReturningTrip())
                            seatService.findById(bookingInfo.getReturningTrip().getId())
                    ));
        }
        model.addObject("bookingInfo", bookingInfo);
        return model;
    }

    /**
     * @param bookingInfo
     * @return
     * @throws RecordNotFoundException
     */
    @RequestMapping(path = "/order/ticket", method = RequestMethod.POST)
    public ModelAndView fillingTicket(@ModelAttribute BookingProcessInfo bookingInfo) throws RecordNotFoundException {
        ModelAndView model = new ModelAndView("redirect:/");
        Order order = orderService.createOrUpdate(new Order(new Date(), bookingInfo.isRoundticket(), bookingInfo.getContactFirstName(), bookingInfo.getContactLastName(), bookingInfo.getContactPhoneNum(), bookingInfo.getContactEmail(), bookingInfo.getContactAddress()));
        for (Ticket ticket : bookingInfo.getAdultTickets()) {
            ticket.setRoundticket(bookingInfo.isRoundticket());
            ticket.setGoTripSeat(seatService.findById(bookingInfo.getDepartingTrip().getId()));
            ticket.setReturnTripSeat(seatService.findById(bookingInfo.getDepartingTrip().getId()));
            ticket.setGoTripPrice(ticket.getGoTripSeat().getAdultPrice());
            ticket.setReturnTripPrice(ticket.getReturnTripSeat().getAdultPrice());
            ticket.setAgeRank(3);
            ticket.setRoundticket(bookingInfo.isRoundticket());
            ticket = ticketService.createOrUpdate(ticket);
        }
        return model;
    }
}
