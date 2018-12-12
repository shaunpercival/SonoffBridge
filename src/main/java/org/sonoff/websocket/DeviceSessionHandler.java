package org.sonoff.websocket;



 import javax.enterprise.context.ApplicationScoped;
 import java.util.ArrayList;
 import java.util.HashSet;
 import java.util.List;
 import java.util.Set;
 import javax.websocket.Session;

  import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger;
 import org.sonoff.model.Device;
 import org.sonoff.utils.SonoffProperties;

 import javax.json.Json;
 import javax.json.JsonObject;
 import javax.json.JsonReader;

import javax.json.spi.JsonProvider;

import java.io.IOException;


@ApplicationScoped
public class DeviceSessionHandler {


    private static int deviceId = 0;
    private static final Set<Session> sessions = new HashSet<>();
    private static Set<Device> devices = new HashSet<>();



    final static Logger logger = LogManager.getLogger(DeviceSessionHandler.class.getName());

    public void addSession(Session session) {
        logger.info("addSession:" + session.getId());
        sessions.add(session);
        for (Device device : devices) {
            logger.info("addSession:Notifing device: " + device.getName());
            JsonObject addMessage = JsonResponseMessage.getAddDeviceJsonObject(device);
            sendToSession(session, addMessage);
        }

    }

    public void removeSession(Session session) {

        sessions.remove(session);
    }

    public List<Device> getDevices() {

        return new ArrayList<>(devices);
    }

    public void handleSonoffDateRequest(Session session, Device device){
         sendToSession( session, JsonResponseMessage.createDateMessage(device));
    }

    public void handleSonoffRegisterRequest(Session session, Device device){


       String type = device.getDeviceId().substring(0,2);
        switch (type) {
            case "01" : device.setType("switch"); break;
            case "02":  device.setType("light") ; break;
            case "03'": device.setType("sensor");
                //temperature and humidity. No timers here;
                break;
            default:
                device.setType("unknown");
                logger.warn("Device type not found");
        }
        if (  device.getId() == 0) {
            device.setId(deviceId);
            deviceId++;
        }

        SonoffProperties.persistDevice(device);
        devices.add(device);
        JsonObject jsonResponse = JsonResponseMessage.createRegisterResponseMessage(device);
        logger.info("RegisterResponse JSON -- " + jsonResponse);
        sendToSession(session, jsonResponse);
    }

    public void handleRefeshDeviceList(Session session){

        devices = SonoffProperties.getDeviceSet();

        for (Device device : devices){
             logger.info("Refreshing device for session" + device.getDeviceId());
             logger.info(device.toString());
             JsonObject jsonRemoveObject = JsonResponseMessage.getDeleteJsonMessage(device);
             sendToSession( session,  jsonRemoveObject);
             JsonObject jsonAddObject = JsonResponseMessage.getAddDeviceJsonObject(device);
             sendToSession( session,  jsonAddObject);
        }



    }


    public void addDevice(Device device) {
        logger.info("here 3");
        if (  device.getId() == 0) {
            device.setId(deviceId);
            deviceId++;
        }
        devices.add(device);
        JsonObject addMessage = JsonResponseMessage.getAddDeviceJsonObject(device);
        logger.info("addDevice::sendToAllConnections: " + device.getName());
        sendToAllConnectedSessions(addMessage);
    }


    public void removeDevice(int id) {
        Device device = getDeviceById(id);
        if (device != null) {
            devices.remove(device);
            JsonObject removeMessage = JsonResponseMessage.getDeleteJsonMessage(device);
            sendToAllConnectedSessions(removeMessage);
        }
    }



    public void toggleDevice(int id) {
        JsonProvider provider = JsonProvider.provider();
        Device device = getDeviceById(id);
        if (device != null) {
            if ("On".equals(device.getStatus())) {
                device.setStatus("Off");
            } else {
                device.setStatus("On");
            }
            JsonObject updateDevMessage = provider.createObjectBuilder()
                    .add("action", "toggle")
                    .add("id", device.getId())
                    .add("status", device.getStatus())
                    .build();
            sendToAllConnectedSessions(updateDevMessage);
        }
    }

    private Device getDeviceById(int id) {
        for (Device device : devices) {
            if (device.getId() == id) {
                return device;
            }
        }
        return null;
    }

    public Device getDevice(String key) {
        for (Device device : devices) {
            if (device.getDeviceId() == key) {
                return device;
            }
        }
        return null;
    }


    private void sendToAllConnectedSessions(JsonObject message) {
        logger.info("sendToAllConnectedSessions: size:" + sessions.size());
        for (Session session : sessions) {
            logger.info("sendToAllConnectedSessions::sendingToSession:"  + session.getId());
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            logger.error(ex.getMessage(),ex);
        }
    }


}