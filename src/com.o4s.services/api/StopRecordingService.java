package src.com.o4s.services.api;

import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.commons.beanutils.BeanUtils;
import src.com.o4s.services.pojo.StopRecordingRequest;
import src.com.o4s.services.setUp.Setup;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

public class StopRecordingService extends Setup {
    String apiUrl= baseUrl1+stopRecordingAPI;
    StopRecordingRequest stopRecordingRequest=new StopRecordingRequest();

    public Response stopRecording(Map<String,Object> map) throws InvocationTargetException, IllegalAccessException {
        //header
        Map<String, String> header = new LinkedHashMap<>();
        if(map.get("contentType").toString().length()>0)
            header.put("Content-Type", map.get("contentType").toString());
        if(map.get("Authorization").toString().equalsIgnoreCase("valid"))
            header.put("Authorization","Bearer "+token);
        else if(map.get("Authorization").toString().equalsIgnoreCase("na")){
            //do nothing
        }
        else header.put("Authorization",map.get("Authorization").toString());

        //Setting body request
        BeanUtils.populate(stopRecordingRequest,map);
        stopRecordingRequest.setOperation(map.get("operation1").toString());
        Gson gson=new Gson().newBuilder().create();
        String requestBody=gson.toJson(stopRecordingRequest);

        String expStatusCode=map.get("statusCode1").toString();
        Response response= getResponse(Setup.RequestType.POST,header,apiUrl,requestBody.toString(),null,null,Integer.parseInt(expStatusCode));
        return response;
    }
}
