package helpers;

import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    public static String base_uri = "http://46.101.62.85/";
    public static String  init(){
        Properties prop = new Properties();
        InputStream input = null;
        try {

            String filename = "application.properties";
            input = Configuration.class.getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return "localhost";
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            Configuration.base_uri =  prop.getProperty("BASE_URI");

        }catch (Exception e ){
            e.printStackTrace();
        }
        return Configuration.base_uri;
    }
}
