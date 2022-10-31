package src.commonServices.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelHelper {
    Object[][] data=null;
    public Object[][] readExcelFile(String sheetName) {
        try {
            //obtaining input bytes from a file
            FileInputStream fis = new FileInputStream(new File("C:\\Users\\TB\\IdeaProjects\\api_automation_100MS\\src\\com.o4s.test\\testData\\test.xls"));
            //creating workbook instance that refers to .xls file
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            //creating a Sheet object to retrieve the object
            HSSFSheet worksheet = wb.getSheet(sheetName);

            //FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
            System.out.println("Number of Rows: " + worksheet.getLastRowNum() + " Number of Cells : "
                    + worksheet.getRow(0).getLastCellNum());
            data= new Object[worksheet.getLastRowNum()][worksheet.getRow(0).getLastCellNum()];

            //Putting Excel data into 2 D object
            for (int i = 1; i <= worksheet.getLastRowNum(); i++) {
                Map<String, Object> dataMap = new HashMap<String, Object>();
                for (int k = 0; k < worksheet.getRow(0).getLastCellNum(); k++) {
                    dataMap.put(worksheet.getRow(0).getCell(k).toString(), worksheet.getRow(i).getCell(k).toString());
                }
                //System.out.println("data Map= "+dataMap);
                data[i - 1][0] = dataMap;
            }
        }
        catch (Exception e){
            System.out.println("Error while reading excel file"+ e.getMessage());
        }
        return data;
    }

    public static void main(String[] args) throws Exception{
        ExcelHelper excelHelper=new ExcelHelper();
        Object[][] result=excelHelper.readExcelFile("a");
        for(int i=0;i<result.length;i++){
            System.out.println("row "+ i + ": "+ result[i][0]);
        }
    }
}
