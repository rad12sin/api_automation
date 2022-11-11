package src.commonServices.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class GoogleSheetAPI {
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    static String googleSheetConfigFile="/src/commonServices/resources/googleSheetConfig.json";
    private static Sheets sheetsService;
    private static String sheetId="1iyV9Cu2gfIsrlV-pu3IBeazTuc6nujP_SPtB5IYf8qU";

    private static Credential authorize() throws IOException, GeneralSecurityException{
        InputStream inputStream= GoogleSheetAPI.class.getResourceAsStream(googleSheetConfigFile);

        GoogleClientSecrets googleClientSecrets=GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(),new InputStreamReader(inputStream));
        List<String> scopes= Arrays.asList(SheetsScopes.SPREADSHEETS);

        GoogleAuthorizationCodeFlow flow=new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport()
                ,JacksonFactory.getDefaultInstance(),googleClientSecrets,scopes).setDataStoreFactory(
                        new FileDataStoreFactory(new File("tokens"))).setAccessType("offline").build();

        com.google.api.client.auth.oauth2.Credential credential=new AuthorizationCodeInstalledApp(flow,new LocalServerReceiver()).authorize("user");
        return credential;
    }

    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        Credential credential = authorize();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),JacksonFactory.getDefaultInstance(), credential).setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        sheetsService=getSheetsService();
        String range="test1";
        ValueRange response=sheetsService.spreadsheets().values().get(sheetId,range).execute();
        List<List<Object>> values = response.getValues();
        if(values==null || values.isEmpty()) System.out.println("Unable to fetch data");
        else{
            for(List<Object> list: values){
                System.out.println(list);
            }
        }

    }
}
