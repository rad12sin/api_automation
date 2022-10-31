package src.com.o4s.services.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import constants.ApiUrlConstants;
import io.restassured.response.Response;
import setUp.Setup;

import java.util.LinkedHashMap;
import java.util.Map;

public class StartRecordingService extends Setup{
    String apiUrl= baseUrl1+startScreenRecording;

    public Response startRecording(Map<String,Object> map) {
        //header
        Map<String, String> header = new LinkedHashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Authorization","Authorization "+accessToken);

        Object body=map.get("body");
        Gson gson=new GsonBuilder().create().newBuilder().create();
        String requestBody=gson.toJson(body);
        String expStatusCode=map.get("statusCode").toString();
        expStatusCode=expStatusCode.substring(0,expStatusCode.length()-2);
        Response response= getResponse(Setup.RequestType.POST,header,apiUrl,requestBody,null,null,Integer.parseInt(expStatusCode));
        return response;
    }
}
