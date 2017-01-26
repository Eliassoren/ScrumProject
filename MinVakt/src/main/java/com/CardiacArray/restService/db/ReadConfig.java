package com.CardiacArray.restService.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by andreasbergman on 11/01/17.
 */
public class ReadConfig {
    InputStream inputStream;
    String [] output;


    public String[] getConfigValues() throws IOException {
        try {
            output = new String[4];
            Properties properties = new Properties();
            String configFile = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(configFile);

            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("File '" + configFile + "' not found.");
            }

           output[0] = properties.getProperty("username");
           output[1] = properties.getProperty("password");
           output[2] = properties.getProperty("emailuser");
           output[3] = properties.getProperty("emailpassword");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
       return output;
    }
}
