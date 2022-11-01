package src.com.o4s.test.apiTest;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import src.com.o4s.services.api.StartRecordingService;
import src.com.o4s.services.setUp.Setup;
import src.commonServices.utils.ExcelHelper;

import java.util.Map;

public class StartRecordingTest extends Setup {
    private StartRecordingService startRecording=new StartRecordingService();
    private ExcelHelper excelHelper=new ExcelHelper();

    @DataProvider(name = "StartRecordingDP")
    public Object[][] startRecordingDP(){
        return excelHelper.readExcelFile("startRecording");
    }

    @Test(dataProvider = "StartRecordingDP")
    private void setStartRecordingTest(Map<String,Object> testDataArray[]) throws Exception{
        Map<String, Object> testData=testDataArray[0];
        //System.out.println("Test Passed"+ testData);
        Response response=startRecording.startRecording(testData);
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
