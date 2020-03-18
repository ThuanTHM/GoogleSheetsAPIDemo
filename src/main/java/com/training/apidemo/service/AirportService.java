package com.training.apidemo.service;

import com.training.apidemo.entity.Airport;
import com.training.apidemo.exception.RecordNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface AirportService {
    Collection<Airport> findAll();
    Page<Airport> findAll(PageRequest p);
    Airport findById(Long id) throws RecordNotFoundException;
    void createOrUpdate(Airport airport);
    void delete(Long id) throws RecordNotFoundException;
    Collection<Airport> filtering(Airport airport);
    Collection<String> findDistinctLocations();
}
