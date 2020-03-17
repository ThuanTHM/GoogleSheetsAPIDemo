package com.training.apidemo.service;

import com.training.apidemo.entity.Flight;
import com.training.apidemo.exception.RecordNotFoundException;
import com.training.apidemo.viewmodel.FlightForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;

public interface FlightService {
    Collection<Flight> findAll();
    Page<Flight> findAll(PageRequest p);
    Flight create(Flight flight);
    Flight create(FlightForm flightForm);
    Flight createOrUpdate(Flight flight);
    Flight save(Flight flight);
    void delete(Long id) throws RecordNotFoundException;
    Collection<Flight> filtering(Flight flight);
    Flight findById(Long id) throws RecordNotFoundException;
}
