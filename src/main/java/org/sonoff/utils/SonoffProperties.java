package org.sonoff.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sonoff.model.Device;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by bearingpoint on 03/12/18.
 */
public class SonoffProperties {

   public static final String SONOFFWS_PROPERTIES = "/home/bearingpoint/wk-devops/SonoffWebSockets/src/main/resources/sonoffws.properties";
   public static final String SONOFF_APP_PROPERTIES = "/home/bearingpoint/wk-devops/SonoffWebSockets/src/main/resources/devices.properties";
   private static Properties applicationProps;
   private static Properties deviceProperties;

   static Logger log = LogManager.getRootLogger();

   public static void loadProperties(){
       try {
           Properties defaultProps = new Properties();
           log.info("Properties - 0");
           FileInputStream in = new FileInputStream(SONOFFWS_PROPERTIES);
           log.info("Properties - 1");
           defaultProps.load(in);
           in.close();
           log.info("Properties - 2");
           // create application properties with default
           applicationProps = new Properties(defaultProps);

           // now load properties
           // from last invocation
           in = new FileInputStream(SONOFF_APP_PROPERTIES);
           log.info("Properties - 3");
           deviceProperties = new Properties();
           deviceProperties.load(in);
           in.close();

           log.info("Properties loaded");

       } catch (FileNotFoundException e) {
           log.error(e);
           log.error(e.fillInStackTrace());
       } catch (IOException e) {
           e.printStackTrace();
           log.error(e);
       }


   }


    static {loadProperties();}


    public static void persisteProperties(Device device){

    }

    public static void persistProperty(String param, String value){
        deviceProperties.put(param,value);
        saveProperties();
    }

    private static void saveProperties(){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(SONOFF_APP_PROPERTIES);
            deviceProperties.store(out, "Updated at: " + new Date().toString());
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getEnvVar(String param){
          String value = System.getenv(param);
          if (value == null) {
               System.out.format("%s=%s%n", param, value);
          }
        return value;
    }


//    public List<Device> getDevices(){
//
//
//        ObjectMapper mapper = new ObjectMapper();
//        InputStream is = Test.class.getResourceAsStream("/devices.json");
//        try {
//            return mapper.readValue(is, Device.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }



}
