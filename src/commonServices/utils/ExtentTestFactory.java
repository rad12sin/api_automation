package src.commonServices.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import groovy.transform.Synchronized;
import org.testng.ITestResult;

import java.io.File;
import java.time.LocalDateTime;

public class ExtentTestFactory {
    private static ThreadLocal<ExtentTest> extentPool = new ThreadLocal<ExtentTest>();
    static String date= String.valueOf(LocalDateTime.now());
    public static String filePath = "C:\\Users\\TB\\IdeaProjects\\api_automation_100MS\\Reports"+"\\ExtentReport_"+date.substring(0,19).replaceAll(":","-")+".html";
    static ExtentReports extent;

    @Synchronized
    public static ExtentTest getExtentTest() {
        return extentPool.get();
    }

    public static void setExtentTest(ExtentTest extentTest) {
        extentPool.set(extentTest);
    }


    /**
     * Load Extent Config xml file
     */
    public static void loadExtentFile() {
        new File(filePath);
        extent = new ExtentReports();
        extent.attachReporter(getHtmlReporter());

    }

    /**
     *
     * @return ExtentHtmlReporter Instance
     */
    private static ExtentSparkReporter getHtmlReporter() {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(filePath);
        htmlReporter.config().setDocumentTitle("100 MS Project");
        htmlReporter.config().setReportName("API Automation");
        htmlReporter.config().setTheme(Theme.DARK);
        return htmlReporter;
    }

    public static void flushReport() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        extent.flush();
    }

    public static void configExtenTest(String className) {

        ExtentTest parent = extent.createTest(className);
        ExtentTestFactory.setExtentTest(parent);
    }

    public void testResultCapture(ITestResult result) {

//        String s = result.getTestClass().getName();
//        String TestClassName = s.substring(s.lastIndexOf(".")+1).trim();


        /**
         * Success Block
         */
        if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentTestFactory.getExtentTest().log(Status.PASS, MarkupHelper.createLabel("🥳 ️👉"+result.getMethod().getMethodName()+" Passed.", ExtentColor.BLACK));
        }

        /**
         * Failure Block
         */
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentTestFactory.getExtentTest().info(MarkupHelper.createLabel(" 😔️ 👇"+result.getMethod().getMethodName()+" Failed.", ExtentColor.BLACK));

            ExtentTestFactory.getExtentTest().fail(getFailureReason(result));

        }
    }

    private String getFailureReason(ITestResult result) {

        String exceptionMsg = "<b>"+"Exception Message : "+"</b>";

        StackTraceElement[] obj = result.getThrowable().getStackTrace();

        if(obj[0].toString().contains("SoftAssert")) {

            String temp[] = result.getThrowable().getMessage().split(":");

            exceptionMsg = exceptionMsg + temp[0]+"<br>";

            String temp1[] = temp[1].split(",");
            for(String msg : temp1) {
                exceptionMsg = exceptionMsg + msg + "<br>";
            }

        }else {
            exceptionMsg = "<b>"+"Exception Message : "+"</b>"+result.getThrowable().getMessage()+"<br>";
        }

        String exceptionSource = exceptionMsg+"<b>"+"Exception Source : "+"</b>";
        for(int i=0; i<obj.length; i++) {

            if(obj[i].getClassName().contains("moj") || obj[i].getClassName().contains("sharechat")) {
                exceptionSource = exceptionSource+obj[i].toString()+"<br>";
            }
        }

        return exceptionSource;
    }
}
