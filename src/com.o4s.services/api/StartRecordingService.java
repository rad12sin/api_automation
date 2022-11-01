package src.com.o4s.services.api;

import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.commons.beanutils.BeanUtils;
import src.com.o4s.services.pojo.Resolution;
import src.com.o4s.services.pojo.StartRecordingRequest;
import src.com.o4s.services.setUp.Setup;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

public class StartRecordingService extends Setup {
    String apiUrl= baseUrl1+startScreenRecording;
    StartRecordingRequest startRecordingRequest=new StartRecordingRequest();
    Resolution resolution=new Resolution();
    public Response startRecording(Map<String,Object> map) throws InvocationTargetException, IllegalAccessException {
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

        //Body request
        BeanUtils.populate(startRecordingRequest,map);
        resolution.setWidth(Integer.parseInt(String.valueOf(map.get("width"))));
        resolution.setHeight(Integer.parseInt(String.valueOf(map.get("height"))));
        startRecordingRequest.setResolution(resolution);
        Gson gson=new Gson().newBuilder().create();
        String requestBody=gson.toJson(startRecordingRequest);

        String expStatusCode=map.get("statusCode").toString();
        Response response= getResponse(Setup.RequestType.POST,header,apiUrl,requestBody.toString(),null,null,Integer.parseInt(expStatusCode));
        return response;
    }
}
