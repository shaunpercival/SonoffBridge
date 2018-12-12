/**
 * https://blog.ipsumdomus.com/sonoff-switch-complete-hack-without-firmware-upgrade-1b2d6632c01

 *
 *
 */



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
 *
 * https://docs.oracle.com/cd/E19798-01/821-1794/gfjwc/index.html
 *
 * http://localhost:8080/sonoffwebsockets/index.html#
 * https://localhost:8181/sonoffwebsockets/index.html#
     * http://localhost:4848
 */


@ApplicationScoped
@ServerEndpoint("/actions")
public class DeviceWebSocketServer {


//    static Logger log;


    final static  Logger logger = LogManager.getLogger(DeviceWebSocketServer.class.getName());


    static{
        logger.info("Getting ready");
        System.out.println("Getting Ready : Print console");
    }

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

    static String J_DEVICE_ID = "deviceid";


    private int getJsonVariableInt(JsonObject jsonMessage ,String param){
        try{
            return Integer.getInteger(jsonMessage.getString("param")).intValue();
        } catch (Exception e) {
            logger.warn("Error extracting param: " + param + " " + e.getMessage());
        }
        return 0;
    }

    private String getJsonVariable(JsonObject jsonMessage ,String paramKey){
        try{
            return jsonMessage.getString(paramKey);
        } catch (Exception e) {
            logger.warn("Error extracting param: " + paramKey + " " + e.getMessage());
        }
        return "";
    }


    @OnMessage
    public void handleMessage(String message, Session session) {

        logger.info( "handleMessage: log4j " + message);
        try (


            JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            String action = jsonMessage.getString("action");
            logger.info("here 0");

            Device device = sessionHandler.getDevice(getJsonVariable(jsonMessage,J_DEVICE_ID));
            switch(action){
                case "refresh" : {
                    sessionHandler.handleRefeshDeviceList(session);
                    break;
                }
                case "date":{
                    sessionHandler.handleSonoffDateRequest(session,device);
                    break;
                }
                case "register": {

                    if ( device == null){
                        device = new Device();
                        device.setAPIKey(getJsonVariable(jsonMessage,JsonResponseMessage.J_API_KEY));
                        device.setDeviceId(getJsonVariable(jsonMessage, J_DEVICE_ID));
                    }
                    device.setName(getJsonVariable(jsonMessage,JsonResponseMessage.J_NAME));
                    device.setVersion(getJsonVariable(jsonMessage, "romVersion"));
                    device.setModel(getJsonVariable(jsonMessage,JsonResponseMessage.J_MODEL));
                    sessionHandler.handleSonoffRegisterRequest(session, device);
                    break;


                }
                case "query": {
                    //device wants information on timers
//                var device = self._knownDevices.find(d=>d.id == data.deviceid);
//                if(!device) {
//                    console.log('ERR | WS | Unknown device ',data.deviceid);
//                } else {
//                        /*if(data.params.includes('timers')){
//                         console.log('INFO | WS | Device %s asks for timers',device.id);
//                         if(device.timers){
//                         res.params = [{timers : device.timers}];
//                         }
//                         }*/
//                    res.params = {};
//                    data.params.forEach(p=>{
//                            res.params[p] = device[p];
//                    });


                    break;
                }
                case "update":
                        break;
                default:
                    logger.warn("Action not found: " + action);

            }

            if ("add".equals(action)) {

                 device = new Device();
                device.setName(jsonMessage.getString("name"));
                device.setDescription(jsonMessage.getString("description"));
                device.setType(jsonMessage.getString("type"));
                if ( !jsonMessage.isNull("id") ) {
                    device.setId(jsonMessage.getInt("id"));
                }
                device.setStatus("Off");
                logger.info("here 1");
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
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}