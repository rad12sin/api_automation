package utils;

import constants.ApiUrlConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {
    public Connection connection;
    public Statement stmt;
    public Statement getDatabaseConnection(){
        Properties prop=new Properties();
        FileInputStream fis= null;
        String databaseUrl= ApiUrlConstants.databaseUrl;
        try {
            fis = new FileInputStream(new File(databaseUrl));
            prop.load(fis);
            String url=prop.getProperty("url").toString();
            String uName=prop.getProperty("uName").toString();
            String password=prop.getProperty("pass").toString();
            //Connecting the database
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(url,uName,password);
            stmt=connection.createStatement();
            System.out.println("Database is connected successfully.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

}
