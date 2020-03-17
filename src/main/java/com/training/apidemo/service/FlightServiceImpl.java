package com.training.apidemo.service;

import com.training.apidemo.entity.Flight;
import com.training.apidemo.exception.RecordNotFoundException;
import com.training.apidemo.jpa.FlightRepository;
import com.training.apidemo.viewmodel.FlightForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;

@Service("flightService")
public class FlightServiceImpl implements FlightService {

    static Logger logger = Logger.getLogger(FlightServiceImpl.class.getName());

    @Autowired
    private FlightRepository flightRepository;

    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public void delete(Long id) throws RecordNotFoundException {
        Optional<Flight> flight = flightRepository.findById(id);

        if (flight.isPresent()) {
            flightRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }

    @Override
    public Collection<Flight> filtering(Flight flight) {

        return null;
    }

    @Override
    public Flight findById(Long id) throws RecordNotFoundException {
        Optional<Flight> flight = flightRepository.findById(id);

        if (flight.isPresent()) {
            return flight.get();
        } else {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }

    public Collection<Flight> findAll() {
        return flightRepository.findAll();
    }

    public Page<Flight> findAll(PageRequest p) {
        return flightRepository.findAll(p);
    }

    @Override
    public Flight create(Flight flight) {
        return null;
    }

    @Override
    public Flight create(FlightForm flightForm) {
        return null;
    }

    @Override
    public Flight createOrUpdate(Flight flight) {
        if (flight.getId() == null) {
            flight = flightRepository.save(flight);
            return flight;
        } else {
            Optional<Flight> curFilght = flightRepository.findById(flight.getId());//current data
            if (curFilght.isPresent()) {
                Flight newEntity = curFilght.get();
                newEntity.setCode(flight.getCode());
                newEntity.setArrivalAirport(flight.getArrivalAirport());
                newEntity.setDepartureAirport(flight.getDepartureAirport());
                newEntity.setArrivalTime(flight.getArrivalTime());
                newEntity.setDepartureTime(flight.getDepartureTime());
                newEntity = flightRepository.save(newEntity);
                return newEntity;
            } else {
                flight = flightRepository.save(flight);
                return flight;
            }
        }
    }

}
