package com.training.apidemo.utils;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SpreadsheetUtils {

    //<editor-fold desc="Constant">
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "GoogleSheetAPIDemo";
    //</editor-fold>

    //<editor-fold desc="Sheets Service Init">
    public Sheets spreadsheets(final NetHttpTransport httpTransport, HttpRequestInitializer credential) {
        // Build a new authorized API client service.
        return new Sheets.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public Sheets spreadsheetsAuth2_0() throws IOException, GeneralSecurityException{
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return spreadsheets(HTTP_TRANSPORT, CredentialsUtils.getOAuth2Credentials(HTTP_TRANSPORT));
    }

    public Sheets spreadsheetsServiceAccount() throws IOException, GeneralSecurityException{
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return spreadsheets(HTTP_TRANSPORT, CredentialsUtils.getServiceAccountCredentials(HTTP_TRANSPORT));
    }
    //</editor-fold>
}
