package com.training.apidemo.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.training.apidemo.entity.Airport;
import com.training.apidemo.exception.RecordNotFoundException;
import com.training.apidemo.jpa.AirportRepository;
import com.training.apidemo.snippets.SpreadsheetSnippets;
import com.training.apidemo.utils.SpreadsheetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service("airportService")
public class AirportServiceImpl implements AirportService {

    private static final String ID_DEMOSHIT = "16ev_ShmFs3NqaBEzi51WeZuXVP_rkbQEnHRpRpKyxW4";
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
    public void createOrUpdate(Airport airport) {
        try {
            Airport newEntity = new Airport();
            SpreadsheetSnippets snippets = new SpreadsheetSnippets(new SpreadsheetUtils().spreadsheetsAuth2_0());
            final String range = "ListAirports";
            if (airport.getId() == null) {
                newEntity = airportRepository.save(airport);
                snippets.appendValues(ID_DEMOSHIT, range, "RAW", Arrays.asList(newEntity.toList()));
                return;
            }
            Optional<Airport> a = airportRepository.findById(airport.getId());
            if (!a.isPresent()) {
                newEntity = airportRepository.save(airport);
                snippets.appendValues(ID_DEMOSHIT, range, "RAW", Arrays.asList(newEntity.toList()));
                return;
            }
            newEntity = a.get();
            newEntity.setName(airport.getName());
            newEntity.setLocation(airport.getLocation());
            newEntity = airportRepository.save(newEntity);
//            snippets.appendValues(ID_DEMOSHIT, range, "RAW", Arrays.asList(newEntity.toList()));
            return;
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(AirportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AirportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Collection<String> findDistinctLocations() {
        return airportRepository.findDistinctLocations(); //To change body of generated methods, choose Tools | Templates.
    }
}
