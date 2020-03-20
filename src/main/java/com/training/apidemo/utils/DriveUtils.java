package com.training.apidemo.utils;

//<editor-fold desc="Import">
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;

import java.io.IOException;
import java.security.GeneralSecurityException;
//</editor-fold>

public class DriveUtils {

    //<editor-fold desc="Constant">
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "GoogleSheetAPIDemo";
    //</editor-fold>

    //<editor-fold desc="Sheets Service Init">
    public Drive drive(final NetHttpTransport httpTransport, HttpRequestInitializer credential) {
        // Build a new authorized API client service.
        return new Drive.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public Drive driveAuth2_0() throws IOException, GeneralSecurityException{
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return drive(HTTP_TRANSPORT, CredentialsUtils.getOAuth2Credentials(HTTP_TRANSPORT));
    }

    public Drive driveServiceAccount() throws IOException, GeneralSecurityException{
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return drive(HTTP_TRANSPORT, CredentialsUtils.getServiceAccountCredentials(HTTP_TRANSPORT));
    }
    //</editor-fold>
}
