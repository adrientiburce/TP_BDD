package lib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    /**
     * enable to read config values from a file
     * @param key of the value in the config file
     * @return the secret value
     */
    public static String getConfigValue(String key) {
        Properties prop = new Properties();
        String fileName = "app.config";
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
            System.out.println("error");
            return "";
        }
        try {
            prop.load(is);
        } catch (IOException ex) {
            System.out.println("error");
            return "";
        }
        return prop.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println("—|—|—|— —");
        System.out.println("—|—|—|—|");


        System.out.println("++++++");
        System.out.println("-------");
    }
}
