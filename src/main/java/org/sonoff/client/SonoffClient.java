package org.sonoff.client;

import org.sonoff.model.Device;
import org.sonoff.websocket.DeviceSessionHandler;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.SimpleFormatter;
import javax.json.JsonObject;
import javax.websocket.*;

//https://www.baeldung.com/websockets-api-java-spring-client
//https://dzone.com/articles/sample-java-web-socket-client


@ClientEndpoint
public class SonoffClient  {
    private static Object waitLock = new Object();

    @OnMessage
    public void onMessage(String message) {
         //the new USD rate arrives from the websocket server side.
        System.out.println("Received msg: "+message);
    }
    private static void  wait4TerminateSignal()
    {
        synchronized(waitLock)
        {try {
            waitLock.wait();
        } catch (InterruptedException e) {
        }}}

    public static void main(String[] args) {

        System.out.println("starting SonoffClient");

        WebSocketContainer container=null;//
        Session session=null;
        try{
            //Tyrus is plugged via ServiceLoader API. See notes above
            container = ContainerProvider.getWebSocketContainer();
           //WS1 is the context-root of my web.app
             //ratesrv is the  path given in the ServerEndPoint annotation on server implementation
            session=container.connectToServer(SonoffClient.class, URI.create("ws://localhost:8080/sonoffwebsockets/actions"));

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            String formatedDate = formatter.format(new Date());

            Random rand = new Random();
            int id = rand.nextInt(50) + 1;

            System.out.println("id; " + id);


             Device device = new Device();
             device.setName("SFC : " + formatedDate);
             device.setId(id);
             device.setStatus("ON");
             device.setType("Appliance");
             device.setDescription("test");

             JsonObject addMessage = new DeviceSessionHandler().createAddDeviceMessage(device);

             session.getAsyncRemote().sendObject(addMessage);


          wait4TerminateSignal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(session!=null){
                try {
                    session.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

//public class SonoffClient {
//
//     public static void main(String []args){
//

//
//
//     }
//
//
//}