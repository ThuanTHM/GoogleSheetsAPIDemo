package com.training.apidemo.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.training.apidemo.service.AirportService;
import com.training.apidemo.snippets.SpreadsheetSnippets;
import com.training.apidemo.utils.DriveUtils;
import com.training.apidemo.utils.SpreadsheetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//</editor-fold>

@ControllerAdvice
@Controller
@RequestMapping(path = "/export")
public class ExportController {
    //<editor-fold desc="Service">
    @Autowired
    private AirportService airportService;
    //</editor-fold>

    //<editor-fold desc="Constant">
    private static final String ID_DEMOSHIT = "16ev_ShmFs3NqaBEzi51WeZuXVP_rkbQEnHRpRpKyxW4";
    private static final String SHITNAME = "ListAirports";
    //</editor-fold>

    //<editor-fold desc="Global Instance">
    /*
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    //</editor-fold>

    @RequestMapping(path = "/airport")
    public ResponseEntity<Object> exportAirport() throws URISyntaxException, IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final String range = SHITNAME+"!A2:C";
        SpreadsheetSnippets snippets = new SpreadsheetSnippets(new SpreadsheetUtils().spreadsheetsAuth2_0());
        List<List<Object>> values = new ArrayList<>();
        airportService.findAll().forEach(airport -> {
            values.add(airport.toList());
        });
        UpdateValuesResponse response2 = snippets.updateValues(ID_DEMOSHIT, range, "RAW", values);
        URI demoshit = new URI("https://docs.google.com/spreadsheets/d/" + ID_DEMOSHIT);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(demoshit);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }
}
