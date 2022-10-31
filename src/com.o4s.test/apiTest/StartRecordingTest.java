package src.com.o4s.test.apiTest;

import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import src.com.o4s.services.api.StartRecordingService;
import src.commonServices.utils.ExcelHelper;

import java.util.Map;

public class StartRecordingTest extends setUp.Setup {
    private StartRecordingService startRecording=new StartRecordingService();
    private ExcelHelper excelHelper=new ExcelHelper();

    @DataProvider(name = "StartRecordingDP")
    public Object[][] startRecordingDP(){
        return excelHelper.readExcelFile("startRecording");
    }

    @Test(dataProvider = "StartRecordingDP")
    private void setStartRecordingTest(Map<String,Object> testDataArray[]){
        Map<String, Object> testData=testDataArray[0];
        //System.out.println("Test Passed"+ testData);
        Response response=startRecording.startRecording(testData);
        if(response.statusCode()==200){

        }
        else{

        }
    }
}
