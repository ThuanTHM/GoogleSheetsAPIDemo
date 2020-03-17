package com.training.apidemo.controller;

//<editor-fold desc="Description">

import com.training.apidemo.entity.Flight;
import com.training.apidemo.entity.Seat;
import com.training.apidemo.exception.RecordNotFoundException;
import com.training.apidemo.service.AirportService;
import com.training.apidemo.service.FlightService;
import com.training.apidemo.service.SeatService;
import com.training.apidemo.viewmodel.OrderForm;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import org.springframework.stereotype.Controller;
//</editor-fold>

@Controller
@RequestMapping(path = {"flight"})
public class FlightController {

    @Autowired
    private FlightService flightService;
    @Autowired
    private AirportService airportService;
    @Autowired
    private SeatService seatService;

    @GetMapping(path = "/manage")
    public ModelAndView getAll(HttpServletRequest request) {
        int page = 0; //default page number is 0 (yes it is weird)
        int size = 6; //default page size is 6
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        ModelAndView model = new ModelAndView("flight_manage");
        model.addObject("flights", flightService.findAll(PageRequest.of(page, size)));
        return model;
    }

    @RequestMapping(path = {"/manage/edit", "/manage/edit/{id}"})
    public String edit(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
        if (id.isPresent()) {
            Flight flight = flightService.findById(id.get());
            model.addAttribute("flightDetail", flight);
        } else {
            model.addAttribute("flightDetail", new Flight(Calendar.getInstance().getTime(), Calendar.getInstance().getTime()));
        }
        model.addAttribute("airports", airportService.findAll());
        model.addAttribute("mindate", (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")).format(Calendar.getInstance().getTime()));
        return "flight_modify";
    }

    @RequestMapping(path = {"/manage/delete/{id}"})
    public String delete(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        flightService.delete(id);
        return "redirect:/flight/manage";
    }

    @RequestMapping(path = "/manage/create", method = RequestMethod.POST) // Map ONLY POST Requests
    public String addFlight(@ModelAttribute Flight flightDetail) {
        flightService.createOrUpdate(flightDetail);
        return "redirect:/flight/manage";
    }

    @RequestMapping(path = {"/manage/{id}/seat"})
    public String getAllSeats(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
        if (id.isPresent()) {
            Flight flight = flightService.findById(id.get());
            model.addAttribute("flight", flight);
        }
        return "flight_seat_manage";
    }

    @RequestMapping(path = {"/manage/{flightId}/seat/edit", "/manage/{flightId}/seat/edit/{id}"})
    public String editSeat(Model model, @PathVariable("flightId") Long flightId, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
        if (id.isPresent()) {
            model.addAttribute("seatDetail", seatService.findById(id.get()));
        } else {
            model.addAttribute("seatDetail", new Seat(flightService.findById(flightId)));
        }
        return "flight_seat_modify";
    }

    @RequestMapping(path = "/manage/seat/create", method = RequestMethod.POST) // Map ONLY POST Requests
    public String addSeat(@ModelAttribute Seat seatDetail) {
        seatDetail = seatService.createOrUpdate(seatDetail);
        return "redirect:/flight/manage/" + seatDetail.getFlight().getId() + "/seat";
    }

    @RequestMapping(path = {"/manage/seat/delete/{id}"})
    public String deleteSeat(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        Long flightId = seatService.findById(id).getFlight().getId();
        seatService.delete(id);
        return "redirect:/flight/manage/" + flightId + "/seat";
    }

    @GetMapping(path = "/booking/search")
    public ModelAndView search() {
        return searchFlightPage();
    }

    @PostMapping(path = "/booking/select-flights") // Map ONLY POST Requests
    public ModelAndView search(@ModelAttribute OrderForm orderForm) {

        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        ModelAndView model = new ModelAndView("flight_select");
        return model;
    }

    public ModelAndView searchFlightPage() {
        ModelAndView model = new ModelAndView("flight_search");
        model.addObject("airports", airportService.findAll());
        model.addObject("orderForm", new OrderForm());
        return model;
    }
}
