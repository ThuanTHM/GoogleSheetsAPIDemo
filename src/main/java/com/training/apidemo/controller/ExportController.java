package com.training.apidemo.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.training.apidemo.service.AirportService;
import com.training.apidemo.utils.SpreadsheetSnippets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private static final String ID_DEMOSHIT = "https://docs.google.com/spreadsheets/d/16ev_ShmFs3NqaBEzi51WeZuXVP_rkbQEnHRpRpKyxW4";
    private static final String APPLICATION_NAME = "GoogleSheetAPIDemo";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    //</editor-fold>

    //<editor-fold desc="Global Instance">
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String AUTH2_0_CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String SERVICE_ACCOUNTS_CREDENTIALS_FILE_PATH = "/serviceaccount.json";
    //</editor-fold>

    @RequestMapping(path = "/airport")
    public ResponseEntity<Object> exportFlight() throws URISyntaxException, IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String range = "Airports!A1:C";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getServiceAccountCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();
        SpreadsheetSnippets snippets = new SpreadsheetSnippets(service);
        String spreadsheetId = "16ev_ShmFs3NqaBEzi51WeZuXVP_rkbQEnHRpRpKyxW4";
        List<List<Object>> values = new ArrayList<>();
        airportService.findAll().forEach(airport -> {
            values.add(airport.toList());
        });
        BatchUpdateSpreadsheetResponse response1 = snippets.batchAddSheetsRequest(spreadsheetId, new String[]{"Airports"});
        UpdateValuesResponse response2 = snippets.updateValues(spreadsheetId, range, "RAW", values);
        URI demoshit = new URI("https://docs.google.com/spreadsheets/d/"+ID_DEMOSHIT);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(demoshit);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }


    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getOAuth2Credentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SheetsQuickstart.class.getResourceAsStream(AUTH2_0_CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + AUTH2_0_CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static HttpRequestInitializer getServiceAccountCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SheetsQuickstart.class.getResourceAsStream(SERVICE_ACCOUNTS_CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + SERVICE_ACCOUNTS_CREDENTIALS_FILE_PATH);
        }
        return new HttpCredentialsAdapter(GoogleCredentials.fromStream(in)
                .createScoped(SCOPES));
    }
}
