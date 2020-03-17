package com.training.apidemo.service;

import com.training.apidemo.entity.Airport;
import com.training.apidemo.exception.RecordNotFoundException;
import com.training.apidemo.jpa.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;

@Service("airportService")
public class AirportServiceImpl implements AirportService {

    static Logger logger = Logger.getLogger(AirportServiceImpl.class.getName());

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public void delete(Long id) throws RecordNotFoundException {
        Optional<Airport> airport = airportRepository.findById(id);

        if (airport.isPresent()) {
            airportRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }

    @Override
    public Collection<Airport> filtering(Airport airport) {
        return null;
    }

    public Collection<Airport> findAll() {
        return airportRepository.findAll();
    }

    public Page<Airport> findAll(PageRequest p) {
        return airportRepository.findAll(p);
    }

    @Override
    public Airport findById(Long id) throws RecordNotFoundException {
        Optional<Airport> airport = airportRepository.findById(id);

        if (airport.isPresent()) {
            return airport.get();
        } else {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }

    @Override
    public Airport createOrUpdate(Airport airport) {
        if (airport.getId() == null) {
            airport = airportRepository.save(airport);

            return airport;
        } else {
            Optional<Airport> a = airportRepository.findById(airport.getId());
            if (a.isPresent()) {
                Airport newEntity = a.get();
                newEntity.setName(airport.getName());
                newEntity.setLocation(airport.getLocation());
                newEntity = airportRepository.save(newEntity);
                return newEntity;
            } else {
                airport = airportRepository.save(airport);
                return airport;
            }
        }
    }

    @Override
    public Collection<String> findDistinctLocations() {
        return airportRepository.findDistinctLocations(); //To change body of generated methods, choose Tools | Templates.
    }
}
