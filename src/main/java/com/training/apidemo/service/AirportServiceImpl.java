package com.training.apidemo.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.DimensionRange;
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

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service("airportService")
public class AirportServiceImpl implements AirportService {

    private static final String ID_DEMOSHIT = "16ev_ShmFs3NqaBEzi51WeZuXVP_rkbQEnHRpRpKyxW4";
    private static final String SHITNAME = "ListAirports";
    static Logger logger = Logger.getLogger(AirportServiceImpl.class.getName());

    @Autowired
    private AirportRepository airportRepository;

    /*
     * delete selected airport in db then delete corresponding row on spreadsheet
     * @param id of airport
     * @throws RecordNotFoundException
     */
    @Override
    public void delete(Long id) throws RecordNotFoundException {
        Optional<Airport> airport = airportRepository.findById(id);
        try {
            if (!airport.isPresent()) {
                throw new RecordNotFoundException("No record exist for given id");
            }
            airportRepository.deleteById(id);
            SpreadsheetSnippets snippets = new SpreadsheetSnippets(new SpreadsheetUtils().spreadsheetsAuth2_0());
            String range = SHITNAME + "!A2:A";
            List<List<Object>> values = snippets.getValueRange(ID_DEMOSHIT, range);
            int modifiedrow = getRowById(id, snippets.getValueRange(ID_DEMOSHIT, range));
            if (modifiedrow < 2) {
                return;
            }
            DimensionRange dimensionRange = new DimensionRange().setSheetId(snippets.getSheetIdByTitle(ID_DEMOSHIT, SHITNAME)).setDimension("ROWS").setStartIndex(modifiedrow-1).setEndIndex(modifiedrow);
            BatchUpdateSpreadsheetResponse response = snippets.batchDeleteDimensionRequest(ID_DEMOSHIT, Arrays.asList(dimensionRange));
            Logger.getLogger(AirportServiceImpl.class.getName()).log(Level.FINE, response.toPrettyString());
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(AirportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AirportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
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
            //<editor-fold desc="Create Airport">
            Airport newEntity = new Airport();
            SpreadsheetSnippets snippets = new SpreadsheetSnippets(new SpreadsheetUtils().spreadsheetsAuth2_0());
            String range = SHITNAME;
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
            range = SHITNAME + "!A2:A";
            List<List<Object>> values = snippets.getValueRange(ID_DEMOSHIT, range);
            int modifiedrow = getRowById(newEntity.getId(), snippets.getValueRange(ID_DEMOSHIT, range));
            if (modifiedrow < 2) {
                snippets.appendValues(ID_DEMOSHIT, SHITNAME, "RAW", Arrays.asList(newEntity.toList()));
                return;
            }
            //</editor-fold>
            //<editor-fold desc="Update Airport">
            Logger.getLogger(AirportServiceImpl.class.getName()).log(Level.FINE, snippets.updateValues(ID_DEMOSHIT, SHITNAME + "!A" + modifiedrow + ":C", "RAW", Arrays.asList(newEntity.toList())).toPrettyString());
            return;
            //</editor-fold>
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(AirportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AirportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getRowById(Long id, List<List<Object>> values) {
        if (values == null || values.isEmpty()) {
            Logger.getLogger(AirportServiceImpl.class.getName()).log(Level.SEVERE, "No sheet's row found");
            return -1;
        }
        return values.indexOf(Arrays.asList(id.toString())) + 2;
    }

    @Override
    public Collection<String> findDistinctLocations() {
        return airportRepository.findDistinctLocations(); //To change body of generated methods, choose Tools | Templates.
    }
}
