package org.sonoff.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sonoff.model.Device;

import java.io.*;
import java.util.*;

/**
 * Created by bearingpoint on 03/12/18.
 */
public class SonoffProperties {


//    public static final String SONOFFWS_PROPERTIES  = "src/test/resources/sonoffws.properties";
//    public static final String SONOFF_APP_PROPERTIES = "src/test/resources/devices.properties";

    private static Logger logger = LogManager.getLogger(SonoffProperties.class.getName());


   public static  String SONOFFWS_PROPERTIES   = "/usr/local/tomcat/conf/sonoffws.properties";
   public static  String SONOFF_APP_PROPERTIES = "/usr/local/tomcat/conf/devices.properties";
   private static Properties applicationProps;
   private static Properties deviceProperties;

    public static void setSonofWSproperties(String value){
        SONOFFWS_PROPERTIES = value;
    }


    public static void setSonoffDeviceproperties(String value){
        SONOFF_APP_PROPERTIES = value;
    }


    public static Properties getApplicationProps() {
        return applicationProps;
    }

    public static void setApplicationProps(Properties applicationProps) {
        SonoffProperties.applicationProps = applicationProps;
    }

    public static Properties getDeviceProperties() {
        return deviceProperties;
    }

    public static void setDeviceProperties(Properties deviceProperties) {
        SonoffProperties.deviceProperties = deviceProperties;
    }


   public static void loadProperties(){
       try {
           Properties defaultProps = new Properties();


           FileInputStream in = new FileInputStream(SONOFFWS_PROPERTIES);
           defaultProps.load(in);
           in.close();

           applicationProps = new Properties(defaultProps);

           // now load properties
           // from last invocation
           in = new FileInputStream(SONOFF_APP_PROPERTIES);
           deviceProperties = new Properties();
           deviceProperties.load(in);
           in.close();

           logger.info("Properties loaded");

       } catch (FileNotFoundException e) {
           logger.error(e.getMessage(),e);
       } catch (IOException e) {
           logger.error(e.getMessage(),e);
       }


   }


    static {loadProperties();}


    public static void persistDevice(Device device){
        loadProperties();
        try {
            String deviceToString = null;
            deviceToString = new ObjectMapper().writeValueAsString(device);
            persistProperty(device.getDeviceId(),deviceToString);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(),e);
        }

    }

    public static Set<Device> getDeviceSet(){
        loadProperties();
        Set<String> keys = deviceProperties.stringPropertyNames();
        Set<Device> devices = new HashSet<>();
        for (String key : keys) {
            String deviceJSONString =  deviceProperties.getProperty(key);
            try {
                Device device = new ObjectMapper().readValue(deviceJSONString, Device.class);
                devices.add(device);
            }catch(Exception e){
                logger.error(e.getMessage(),e);
            }


        }
        return devices;

    }


    public static void persistProperty(String param, String value){
        deviceProperties.put(param,value);
        saveProperties();
        logger.info("persistProperty: " + param);
    }

    private static void saveProperties(){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(SONOFF_APP_PROPERTIES);
            deviceProperties.store(out, "Updated at: " + new Date().toString());
            out.close();

        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }

    }

    public static String getProperty(String key){
        String value = applicationProps.getProperty(key);
        if (value == null) {
            System.out.format("%s=%s%n", key, value);
        }
        return value;
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
