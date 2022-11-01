package src.com.o4s.test.apiTest;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import src.com.o4s.services.api.StopRecordingService;
import src.com.o4s.services.setUp.Setup;
import src.commonServices.utils.ExcelHelper;

import java.util.Map;

public class StopRecordingTest extends Setup {
    private StopRecordingService stopRecordingService=new StopRecordingService();
    private ExcelHelper excelHelper=new ExcelHelper();

    @DataProvider(name = "StopRecordingDP")
    public Object[][] stopRecordingDP(){
        return excelHelper.readExcelFile("stopRecording");
    }

    @Test(dataProvider = "StopRecordingDP")
    private void stopRecordingTest(Map<String,Object> testDataArray[]) throws Exception{
        Map<String, Object> testData=testDataArray[0];
        Response response=stopRecordingService.stopRecording(testData);
        System.out.println(response.asString());
        if(response.statusCode()==200){
            Assert.assertEquals(response.asString().substring(1,response.asString().length()-2),testData.get("expResponse"), "Expected and Actual response did not match");
        }
        else{
            String actual=response.asString().replaceAll("\"","");
            actual=actual.replaceAll("\n","");
            Assert.assertEquals(testData.get("expResponse"),actual, "Expected and Actual response did not match for Negative case");
        }
    }
}
