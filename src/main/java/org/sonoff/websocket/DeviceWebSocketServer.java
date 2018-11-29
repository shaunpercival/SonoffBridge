package org.sonoff.websocket;


//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.logging.LogManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.InputStream;


import org.sonoff.model.Device;

/**
 * https://www.oracle.com/webfolder/technetwork/tutorials/obe/java/HomeWebsocket/WebsocketHome.html#section4
 */


@ApplicationScoped
@ServerEndpoint("/actions")
public class DeviceWebSocketServer {


//    static Logger log;


    static Logger logger = LogManager.getRootLogger();



    //@Inject
    private DeviceSessionHandler sessionHandler = new DeviceSessionHandler();

//    static {
//        System.out.println("DeviceWebSocketServer::logging setup start");
//        InputStream stream = DeviceWebSocketServer.class.getClassLoader().getResourceAsStream("logging.properties");
//        try {
//            LogManager.getLogManager().readConfiguration(stream);
//            log = Logger.getLogger(DeviceWebSocketServer.class.getName());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        log.info("DeviceWebSocketServer::logging setup");
//        System.out.println("DeviceWebSocketServer::logging setup");
//    }


    @OnOpen
    public void open(Session session) {
        logger.info("Add session: " + session.getId());
        sessionHandler.addSession(session);

    }

    @OnClose
    public void close(Session session) {
        logger.info("Close session: " + session.getId());
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        logger.error( error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {

        logger.info( "handleMessage: log4j " + message);
        try (


            JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            if ("add".equals(jsonMessage.getString("action"))) {
                Device device = new Device();
                device.setName(jsonMessage.getString("name"));
                device.setDescription(jsonMessage.getString("description"));
                device.setType(jsonMessage.getString("type"));
                device.setId(jsonMessage.getInt("id"));
                device.setStatus("Off");
                sessionHandler.addDevice(device);
            }

            if ("remove".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.removeDevice(id);
            }

            if ("toggle".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.toggleDevice(id);
            }
        }
    }
}