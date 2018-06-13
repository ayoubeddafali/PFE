package uk.co.danielbryant.djshopping.productcatalogue.helpers;

import java.io.InputStream;
import java.util.Properties;

public class DBConfiguration {
    public static String uid;
    public static String pwd;
    public static String database ;
    public static String cat ;
    public static String mysql_db_url;

    public static void  init(){
        Properties prop = new Properties();
        InputStream input = null;
        try {

            String filename = "application.properties";
            input = DBConfiguration.class.getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            DBConfiguration.uid =  prop.getProperty("uid");
            DBConfiguration.database = prop.getProperty("database");
            DBConfiguration.cat = prop.getProperty("cat");
            DBConfiguration.pwd =  prop.getProperty("password");
            DBConfiguration.mysql_db_url = prop.getProperty("mysql_db_url");

        }catch (Exception e ){
            e.printStackTrace();
        }
    }
}
