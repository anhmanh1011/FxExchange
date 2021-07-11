package com.api.orderfx.ApplicationListener;

import com.api.orderfx.RestClientRequest.FXCMRequestClient;
import com.api.orderfx.Utils.JsonUtils;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
public class SocketConnectFXCM implements
        ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LogManager.getLogger(SocketConnectFXCM.class);


    @Value("${fxcm.api.host}")
    private String trading_api_host;
    @Value("${fxcm.api.port}")
    private String trading_api_port;
    @Value("${fxcm.uri.connection}")
    private String url_connection;
    @Value("${fxcm.api.access_token}")
    private String login_token;
    public static Socket server_socket;
    public static String bearer_access_token;

    @Value("${fxcm.api.account.id}")
    public static String accountId;

    @Autowired
    FXCMRequestClient fxcmRequestClient;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        IO.Options options = new IO.Options();
        options.forceNew = true;

        final OkHttpClient client = new OkHttpClient();
        options.webSocketFactory = client;
        options.callFactory = client;
        options.query = "access_token=" + login_token;

        options.timeout = 30000;
        options.reconnection = true;
        options.reconnectionAttempts = 10;
        options.reconnectionDelay = 10000;

        //final Socket socket = IO.socket(url + ":" + port, options);
        server_socket = IO.socket(url_connection, options);
        server_socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                bearer_access_token = "Bearer " + server_socket.id() + login_token;
                log.debug("bearer_access_token: " + bearer_access_token);
                log.debug("bearer token: " + bearer_access_token);
                try {
                    getDataAccount();
                    subscribeModels();
//                    sendMarketOrder();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        server_socket.on(Socket.EVENT_RECONNECTING, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                log.info(JsonUtils.ObjectToJson(objects));
                log.info("====================== EVENT_RECONNECTING ==================");
            }
        });

        server_socket.on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                log.info(JsonUtils.ObjectToJson(objects));
                log.info("====================== Reconnection ==================");
            }
        });

        server_socket.on("Offer", args -> log.info("Offer" + JsonUtils.ObjectToJson(args)));
        server_socket.on("Order", args -> log.info("Order" + JsonUtils.ObjectToJson(args)));
        server_socket.on("OpenPosition", args -> log.info("OpenPosition" + JsonUtils.ObjectToJson(args)));
        server_socket.on("ClosedPosition", args -> log.info("ClosedPosition" + JsonUtils.ObjectToJson(args)));
        server_socket.on("Order", args -> log.info("Order"+JsonUtils.ObjectToJson(args)));
        server_socket.on("Account", args -> log.info("Account"+JsonUtils.ObjectToJson(args)));
        server_socket.on("Summary", args -> log.info("Summary"+JsonUtils.ObjectToJson(args)));
        server_socket.on("LeverageProfile", args -> log.info("LeverageProfile"+JsonUtils.ObjectToJson(args)));
        server_socket.on("Properties", args -> log.info("Properties"+JsonUtils.ObjectToJson(args)));

        server_socket.io().on(io.socket.engineio.client.Socket.EVENT_CLOSE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                log.debug("engine close");
//                client.dispatcher().executorService().shutdown();
            }
        });
        server_socket.open();
    }

    public String sendGetRequest(String path) throws Exception {
        final String path2 = url_connection + path;
        final URL url = new URL(path2);

        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Set the appropriate header fields in the request header.
        connection.setRequestMethod("GET");

        connection.setRequestProperty("Authorization", bearer_access_token);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("host", trading_api_host);
        connection.setRequestProperty("port", trading_api_port);
        connection.setRequestProperty("path", path);
        connection.setRequestProperty("User-Agent", "request");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded'");

        final String response = getResponseString(connection);
        final int responseCode = connection.getResponseCode();

        System.out.println("\nSending 'GET' request: " + path2);
        System.out.println("Response Code : " + responseCode);

        if (responseCode == 200) {
            return response;
        } else {
            throw new Exception(response);
        }
    }

    public String sendPostRequest(String path, String param) {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(url_connection + path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length", "" + Integer.toString(param.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setRequestProperty("Authorization", bearer_access_token);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("host", trading_api_host);
            connection.setRequestProperty("port", trading_api_port);
            connection.setRequestProperty("path", path);
            connection.setRequestProperty("User-Agent", "request");


            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(param);
            wr.flush();
            wr.close();

            //Get Response
            final String response = getResponseString(connection);

            /*
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
              response.append(line);
              response.append('\r');
            }
            rd.close();
            */

            System.out.println("\nSending 'POST' request: " + path);
            System.out.println("Response : " + response);

            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String getResponseString(HttpURLConnection conn) throws IOException {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            final StringBuilder stringBuffer = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append('\r');
            }
            reader.close();
            return stringBuffer.toString();
        }
    }

//
//    static void sendMarketOrder() throws Exception {
//        if (accountID == "") {
//            System.out.println("Account ID missing");
//        } else {
//            String cmd = "account_id=" + accountID +
//                    "&symbol=EUR%2FUSD" +
//                    "&is_buy=true" +
//                    "&rate=0" +
//                    "&amount=2" +
//                    "&order_type=AtMarket" +
//                    "&time_in_force=GTC";
//
//            sendPostRequest("/trading/open_trade", cmd);
//        }
//    }
//


    void getDataAccount() throws Exception {
        LinkedHashMap linkedHashMap = fxcmRequestClient.sendGetRequest("/trading/get_model?models=Account");
        log.info(JsonUtils.ObjectToJson(linkedHashMap));
    }

    void subscribeModels() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("models", "Order");
        LinkedHashMap linkedHashMap = fxcmRequestClient.sendPostRequest("/trading/subscribe", hashMap);
        log.info(JsonUtils.ObjectToJson(linkedHashMap));
        hashMap.clear();
        hashMap.put("models", "OpenPosition");
        linkedHashMap = fxcmRequestClient.sendPostRequest("/trading/subscribe", hashMap);
        log.info(JsonUtils.ObjectToJson(linkedHashMap));
    }
}
