package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {
    Properties prop=new Properties();

    public Properties ReadPropertyFile(String filePath){
        FileInputStream fis= null;
        try {
            fis = new FileInputStream(new File(filePath));
            prop.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
