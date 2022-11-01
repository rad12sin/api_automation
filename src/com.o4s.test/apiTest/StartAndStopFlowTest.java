package src.com.o4s.test.apiTest;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import src.com.o4s.services.api.StartRecordingService;
import src.com.o4s.services.api.StopRecordingService;
import src.com.o4s.services.setUp.Setup;
import src.commonServices.utils.ExcelHelper;

import java.util.Map;

public class StartAndStopFlowTest extends Setup {
    private StopRecordingService stopRecordingService=new StopRecordingService();
    private StartRecordingService startRecordingService=new StartRecordingService();

    private ExcelHelper excelHelper=new ExcelHelper();

    @DataProvider(name = "FlowTestDP")
    public Object[][] startRecordingDP(){
        return excelHelper.readExcelFile("flowTest");
    }

    @Test(dataProvider = "FlowTestDP")
    private void startAndStopRecordingFlowTest(Map<String,Object> testDataArray[]) throws Exception{
        Map<String, Object> testData=testDataArray[0];
        Response stopRecordingResponse;
        Response startRecordingResponse;
        loggerReport.info("************************* Start Recording *************************");
        startRecordingResponse=startRecordingService.startRecording(testData);
        if(startRecordingResponse.getStatusCode()==200){
            loggerReport.info("Validate the positive message for start recording api");
            Assert.assertEquals(startRecordingResponse.asString().substring(1,startRecordingResponse.asString().length()-2),testData.get("expResponseForStartRecording"), "Expected and Actual response did not match");

            loggerReport.info("************************* Stop Recording *************************");
            stopRecordingResponse=stopRecordingService.stopRecording(testData);
            loggerReport.info("Validate the positive message for stop recording api");
            Assert.assertEquals(stopRecordingResponse.asString().substring(1,stopRecordingResponse.asString().length()-2),testData.get("expResponseForStopRecording"), "Expected and Actual response did not match");

        }
        else{
            loggerReport.info("Recording is already started, Validate the negative message for start recording api");
            String actual=startRecordingResponse.asString().replaceAll("\"","");
            actual=actual.replaceAll("\n","");
            Assert.assertEquals("rpc error: code = AlreadyExists desc = beam already started",actual, "Expected and Actual response did not match for Negative case");

            loggerReport.info("************************* recording is already started now Stop the Recording *************************");
            stopRecordingResponse=stopRecordingService.stopRecording(testData);
            Assert.assertEquals(stopRecordingResponse.asString().substring(1,stopRecordingResponse.asString().length()-2),"", "Expected and Actual response did not match");
        }
    }
}
