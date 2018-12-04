package org.sonoff.websocket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.websocket.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sonoff.model.Device;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReader;
import javax.json.JsonObjectBuilder;

import javax.json.spi.JsonProvider;
import java.util.Date;

public class JsonResponseMessage {

    static String J_DEVICE_ID = "deviceid";
    static String J_API_KEY = "apikey";
    static String J_ERROR = "error";
    static String J_DATE = "date";
    static String J_IP = "IP";
    static String J_PORT = "PORT";
    static String J_REASON = "reason";



    private static JsonObjectBuilder getSonoffBaseResponse(Device device){
        JsonProvider provider = JsonProvider.provider();
        JsonObjectBuilder  builder = provider.createObjectBuilder();
        builder.add(J_DEVICE_ID, device.getId());
        builder.add(J_API_KEY, device.getAPIKey());
        builder.add(J_ERROR, "0");

        return builder;
    }

    public static JsonObject createDateMessage(Device device) {

        JsonObjectBuilder builder = getSonoffBaseResponse(device);

        SimpleDateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        builder.add(J_DATE, sdfISO.format(new Date()));

        JsonObject jsonMessage = builder.build();
        return jsonMessage;
    }



    public static JsonObject createRegisterResponseMessage(Device device) {

        JsonObjectBuilder builder = getSonoffBaseResponse(device);

        SimpleDateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        builder.add(J_IP, sdfISO.format(new Date()));
        builder.add(J_PORT, "");
        builder.add(J_REASON, "ok");
        JsonObject jsonMessage = builder.build();
        return jsonMessage;
    }


}