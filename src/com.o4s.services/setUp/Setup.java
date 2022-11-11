package src.com.o4s.services.setUp;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import src.commonServices.utils.ExtentTestFactory;
import src.commonServices.utils.SendFileEmail;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.config;


public class Setup extends constants.ApiUrlConstants {

    int expectedMaxAPITimeTaken=2000; //time in milli seconds
    int reTryCount=1;
    Response response;
    public static ITestContext ctx;
    public static ExtentTest loggerReport;
    public static String sourceMethod;
    public static String testCaseName = "";
    public static String sourceClass;
    static ExtentTestFactory extentTestFactory=new ExtentTestFactory();
    @BeforeSuite(alwaysRun = true)
    public void loadExtentFile() {
        extentTestFactory.loadExtentFile();
    }
    @BeforeMethod(alwaysRun = true)
    public void init(Method method, Object[] details, ITestContext ctx) {
        this.ctx=ctx;
        this.sourceMethod=method.getName();
        sourceClass=getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
        @SuppressWarnings("unchecked")
        Map<String, String> map[] = (Map<String, String>[]) details[0];
        if (map[0].containsKey("Test case")) {
            testCaseName = "-" + map[0].get("Test case");
        } else {
            System.out.println("Please add \"Test case\" column in your data provider excel sheet. "
                    + "\n This will be appended to the method name in the extent report");
        }
        extentTestFactory.configExtenTest(sourceClass + " - " + sourceMethod + testCaseName);
        ctx.setAttribute("testName", sourceMethod + testCaseName);
        this.loggerReport = extentTestFactory.getExtentTest();
    }

    @AfterMethod
    public void captureTheResult(ITestResult result){
        extentTestFactory.testResultCapture(result);
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite(ITestContext context) throws InterruptedException {
        extentTestFactory.flushReport();
        new SendFileEmail();
    }

    public enum RequestType {
        GET, POST, PUT, DELETE, PATCH;
    }

    //Method to hit API call and return the response
    public Response getResponse(final RequestType requestType, final Map<String, String> headerMap, final String requestURL,
                                          final String requestBody,final Map<String, String> pathParam, final Map<String, String> queryParam, final int expectedStatusCode) {

        RequestSpecification spec = RestAssured.given().config(config);
        loggerReport.info("API URL -> "+ requestURL);
        if(headerMap!=null){
            loggerReport.info("Headers - "+headerMap);
            spec.headers(headerMap);
        }

        if(requestBody!=null){
            loggerReport.info("Request Body - "+requestBody);
            spec.body(requestBody);
        }

        if(pathParam!=null){
            loggerReport.info("Path Params - "+pathParam);
            spec.pathParams(pathParam);
        }

        if(queryParam!=null){
            loggerReport.info("Query Params - "+queryParam);
            spec.queryParams(queryParam);
        }

        int counter = 0;
        do {
            if (counter != 0) {
                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException exception){
                    System.out.println(exception.getMessage());
                }
                System.out.println("Response : " + response.asString());
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("+++++++++++++++++++++++++       Retrying            +++++++++++++++++++++");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
           }

            try {
                switch (requestType){

                    case POST:
                        response = spec.log().all().when().post(requestURL);
                        break;
                    case GET:
                        response = spec.log().all().when().get(requestURL);
                        break;
                    case PUT:
                        response =  spec.log().all().when().put(requestURL);
                        break;
                    case DELETE:
                        response =  spec.log().all().when().delete(requestURL);
                        break;
                    case PATCH:
                        response =  spec.log().all().when().patch(requestURL);
                        break;
                    default:
                        return null;
                }
            }catch(Exception e) {
                System.out.println("+++++++++++++++++++++++++++++++++++++++++ Exception Occoured ++++++++++++++++++++++++++++++++");
            }

            counter = counter + 1;

        } while (response.statusCode() != expectedStatusCode && counter < reTryCount);

        if (expectedStatusCode != 1000) {
            loggerReport.info("API Time Taken in MS ************ "+response.getTimeIn(TimeUnit.MILLISECONDS));
            if(response.getTimeIn(TimeUnit.MILLISECONDS)>expectedMaxAPITimeTaken)
                loggerReport.warning(MarkupHelper.createLabel("API is taking more time than expected and expected time is "+ expectedMaxAPITimeTaken
                        +" and actual is "+ response.getTimeIn(TimeUnit.MILLISECONDS), ExtentColor.BROWN));
            if(!response.getContentType().contains("html"))
                loggerReport.info("Response - "+response.asString());
            if(response.getStatusCode()==401 && response.asString().contains("token is expired by")){
                //generate token again and set it for future
                System.out.println("Token is expired, Please generate again while running class GenerateManagementToken and set it in APIUrlConstants file and re-execute the suite");
                System.exit(0);
            }
            validateResult(expectedStatusCode, response.statusCode(), "Status Code Validation");
        }


        return response;
    }

    public  void validateResult(final int expString, final int actualString, final String message) {
        Assert.assertEquals(actualString, expString, message);
    }
}
