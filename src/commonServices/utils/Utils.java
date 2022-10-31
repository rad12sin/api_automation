package utils;

import io.restassured.response.Response;
import org.testng.Assert;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    public static String getDateFromCurentDate(int x, String format) {

        DateFormat dateFormat = new SimpleDateFormat(format);

        // Create a calendar object with today date. Calendar is in java.util package.
        Calendar calendar = Calendar.getInstance();

        // Move calendar to yesterday
        // x is a no. from refernce to current date which means
        // -1 means yesterday
        // 1 means tomorrow
        calendar.add(Calendar.DATE, x);
        TimeZone.setDefault(TimeZone.getTimeZone("IST"));
        //TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        // Get current date of calendar which point to the yesterday now
        Date date = calendar.getTime();

        return dateFormat.format(date).toString();
    }

    public static Date convertStringDateToDateFormat(String date, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return (dateFormat.parse(date));

    }

    public static Date getDateFormatDatefromCurrentDate(int days, String format) throws ParseException {

        // get Calendar instance
        Calendar calender = Calendar.getInstance();
        calender.setTime(new Date());
        // Move calendar to yesterday
        // x is a no. from refernce to current date which means
        // -1 means yesterday
        // 1 means tomorrow
        calender.add(Calendar.DATE, days);

        DateFormat formatter = new SimpleDateFormat(format);

        // convert to date
        Date date = calender.getTime();

        Date todayWithZeroTime = formatter.parse(formatter.format(date));

        return todayWithZeroTime;
    }

    public static int RowCount(ResultSet rs) throws SQLException {
        int size = 0;
        {
            if (rs == null) {
                size = 0;
            } else {
                rs.last();
                size = rs.getRow();
            }
        }
        return size;
    }

    public static boolean write(String fileName, String content) {
        File file = new File(System.getProperty("user.dir") + "\\data\\" + fileName); // Your file
        try {
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean write(String fileName, byte[] content) {
        File file = new File(System.getProperty("user.dir") + "\\data\\" + fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(content);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static Date getDateFormatDatefromCurrentDateforMinutes(int minutes) {

        // get Calendar instance
        Calendar calender = Calendar.getInstance();
        calender.setTime(new Date());
        // Move calendar to yesterday
        // x is a no. from refernce to current date which means
        // -1 means 1 minute less from new Date
        // 1 means 1 minute more from new Date
        calender.add(Calendar.MINUTE, minutes);


        // convert to date
        Date date = calender.getTime();

        return date;
    }

    public static String getJsonFilePath() {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
            + File.separator + "java" + File.separator + "com" + File.separator + "lendingkart" + File.separator + "apitestautomation" + File.separator + "testdata" + File.separator + "jsonFiles";
        return path;
    }

    public static Long splitLeadId(String leadId) {
        Long newLeadId = null;
        String[] list = leadId.split("-");
        if (list.length == 2) {
            String leadIdSplit = list[1];
            newLeadId = Long.parseLong(leadIdSplit);
        } else
            System.out.println("Lead Id is not valid");
        return newLeadId;
    }

    public static void ServiceUnavailableResp(Response response) {
        String regex = "^5[0-9]{2}$";
        if (String.valueOf(response.getStatusCode()).matches(regex)) {
            System.out.println("Service is unavailable");
        }
    }

    public static void ClientValidationError(Response response) {
        String regex = "4[0-9][0-9]{2}$";//regular expression
        if (String.valueOf(response.getStatusCode()).matches(regex)) {
           // logger.error("Client side validation failed " + response.getStatusCode());
            Assert.fail();
        }
    }


        public static boolean responseCodeValidation (String responseCode){
            String responseRex = "2[0-9][0-9]"; //given in regular expression(regex) format
            if ((responseCode.matches(responseRex))) {
                return true;
            } else {
                return false;
            }
        }

        public static String getxlPath(){
            String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "java" + File.separator + "com" + File.separator + "lendingkart" + File.separator + "apitestautomation" + File.separator + "testdata" + File.separator + "xlsxFiles";
            return path;
        }

    public static Date getTimeStampDatefromCurrentDate(int days, String format) {

        // get Calendar instance
        Calendar calender = Calendar.getInstance();
        calender.setTime(new Date());
        // Move calendar to yesterday
        // x is a no. from refernce to current date which means
        // -1 means yesterday
        // 1 means tomorrow
        calender.add(Calendar.DATE, days);

        Timestamp ts = new Timestamp(calender.getTimeInMillis());

        Date todayWithZeroTime = ts;

        return todayWithZeroTime;
    }
    }

