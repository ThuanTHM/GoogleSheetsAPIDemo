package com.training.apidemo.controller;

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
import com.training.apidemo.utils.SpreadsheetSnippets;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class SheetsQuickstart {
    private static final String APPLICATION_NAME = "GoogleSheetAPIDemo";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String AUTH2_0_CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String SERVICE_ACCOUNTS_CREDENTIALS_FILE_PATH = "/serviceaccount.json";

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

    public List<List<Object>> getValuesInRange(Sheets service, String spreadsheetId, String range) throws IOException {
        return service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute().getValues();
    }

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
        final String range = "Class Data!A1:F";
        final String range2 = "Class!A1:F";
//        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getOAuth2Credentials(HTTP_TRANSPORT))
//                .setApplicationName(APPLICATION_NAME)
//                .build();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getServiceAccountCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();
        SpreadsheetSnippets snippets = new SpreadsheetSnippets(service);
//        String spreadsheetId2 = snippets.create("demo1");
        String spreadsheetId2 = "16ev_ShmFs3NqaBEzi51WeZuXVP_rkbQEnHRpRpKyxW4";
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
//        UpdateValuesResponse response1 = new UpdateValuesResponse();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
            return;
        }
//        response1 = snippets.updateValues(spreadsheetId2, range2, "RAW", values);
        UpdateValuesResponse  response1 = snippets.updateValues(spreadsheetId2, range2, "RAW", values);
        BatchUpdateSpreadsheetResponse response2 = snippets.batchAddSheetsRequest(spreadsheetId2, new String[]{"Class 2"});
        values = service.spreadsheets().values()
                .get(spreadsheetId2, range2)
                .execute().getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
            return;
        }
        for (List row : values) {
            // Print columns A and E, which correspond to indices 0 and 4.
//                System.out.printf("%s, %s\n", row.get(0), row.get(4));
            for (Object e : row) {
                System.out.printf("%s, ", e);
            }
            System.out.printf("\n");
        }
    }
}