package org.sonoff.org.sonoff.httpserver;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.util.*;

public class HTTPSonoffEmulator {

    private static int port = 80;

    private  void startServer(){
        try {

            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("server started at " + port);
            server.createContext("/", new RootHandler());
            server.createContext("/device", new DeviceHandler());
            server.createContext("/ap", new APPostHandler());
            server.setExecutor(null);
            server.start();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public class RootHandler implements HttpHandler {

        @Override

        public void handle(HttpExchange he) throws IOException {


            // parse request
            Map<String, Object> parameters = new HashMap<String, Object>();
            InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String query = br.readLine();

            System.out.println("query: " + query);

            String response = "<h1>Server start success"+
            "if you see this message</h1>" + "<h1>Port: " + port + "</h1>";
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static void main(String[] args) {

        if ( args.length == 1){
            port = Integer.getInteger(args[0]);
        }else{
            System.out.println("Using default port " + port);
        }
        HTTPSonoffEmulator server = new HTTPSonoffEmulator();
        server.startServer();

    }

    public class DeviceHandler implements HttpHandler {

        @Override

        public void handle(HttpExchange he) throws IOException {
            // parse request
            Map<String, Object> parameters = new HashMap<String, Object>();
            String response = "{ \"deviceid\":\"1000222565\", \"apikey\": \"cea052a6-4a49-41f4-be5b-f3db537376bc\", \"accept\":\"post\" }";
            Headers headers = he.getResponseHeaders();
            headers.set("Content-Type", "application/json");
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }
    }


    public class APPostHandler implements HttpHandler {

        @Override

        public void handle(HttpExchange he) throws IOException {
            // parse request
            Map<String, Object> parameters = new HashMap<String, Object>();
            InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String query = br.readLine();

            System.out.println("query: " + query);


            parseQuery(query, parameters);

            // send response
            String response = "";
            for (String key : parameters.keySet())
                response += key + " = " + parameters.get(key) + "\n";
            System.out.println("Inputs: " + response);
            response = "{ \"deviceid\":\"1000222565\", \"apikey\": \"cea052a6-4a49-41f4-be5b-f3db537376bc\", \"accept\":\"post\" }";


            Headers headers = he.getResponseHeaders();
            headers.set("Content-Type", "application/json");
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }
    }





    public static void parseQuery(String query, Map<String,Object> parameters) throws UnsupportedEncodingException {

        if (query != null) {
            String pairs[] = query.split("[&]");
            for (String pair : pairs) {
                String param[] = pair.split("[=]");
                String key = null;
                String value = null;
                if (param.length > 0) {
                    key = URLDecoder.decode(param[0],
                            System.getProperty("file.encoding"));
                }

                if (param.length > 1) {
                    value = URLDecoder.decode(param[1],
                            System.getProperty("file.encoding"));
                }

                if (parameters.containsKey(key)) {
                    Object obj = parameters.get(key);
                    if (obj instanceof List<?>) {
                        List<String> values = (List<String>) obj;
                        values.add(value);

                    } else if (obj instanceof String) {
                        List<String> values = new ArrayList<String>();
                        values.add((String) obj);
                        values.add(value);
                        parameters.put(key, values);
                    }
                } else {
                    parameters.put(key, value);
                }
            }
        }
    }

}