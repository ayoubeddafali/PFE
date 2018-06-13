package uk.co.danielbryant.djshopping.productcatalogue.helpers;


import java.io.InputStream;
import java.util.Properties;

public abstract class DBUserInfo {
    private String uid;
    private String pwd;
    private String cat;

    public DBUserInfo() {
    }

    public DBUserInfo(String uid, String pwd, String cat) {

        this.uid = uid;
        this.pwd = pwd;
        this.cat = cat;
    }

    public String getUserID()
    {
        return uid;
    }

    public void setUserID(String value)
    {
        uid = value;
    }

    public String getPassword()
    {
        return pwd;
    }

    public void setPassword(String value)
    {
        pwd = value;
    }

    public String getCatalog()
    {
        return cat;
    }

    public void setCatalog(String catalog)
    {
        cat = catalog;
    }
    public String getDbHost(){
        Properties prop = new Properties();
        InputStream input = null;
        try {

            String filename = "application.properties";
            input = DBUserInfo.class.getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return "localhost:3306";
            }

            //load a properties file from class path, inside static method
            prop.load(input);
            String database = prop.getProperty("database"); // -> db:3306

            return database;
            //get the property value and print it out
        }catch (Exception e ){
            e.printStackTrace();
        }
        return "localhost:3306";
    }
}
