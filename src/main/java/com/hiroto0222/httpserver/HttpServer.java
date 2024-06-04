package com.hiroto0222.httpserver;

import com.hiroto0222.httpserver.config.Configuration;
import com.hiroto0222.httpserver.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Driver Class for HTTP Server
 * 1. needs to be listening to at least 1 port
 *  - unencrypted traffic on 80
 *  -> read configuration files
 * 2. make TCP socket connection on port with client
 *  - makes connection with 1 client only
 * 3. read request messages following HTTP protocols
 * 4. open/read files from FS based on request
 * 5. write response message following HTTP protocols
 * 6. send response to client
 * 7. close TCP socket connection on port
 */
public class HttpServer {
    public static void main(String[] args) {
        System.out.println("Server starting...");

        // load configurations
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("Using Port: " + conf.getPort());
        System.out.println("Using WebRoot: " + conf.getWebroot());

        // create TCP socket on port
        try (ServerSocket serverSocket = new ServerSocket(conf.getPort())) {
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // TODO: read request

            // write response
            String html = """
            <html><head><title>Simple Java HTTP Server</title></head><body><h1>This page was served using Simple Java HTTP Server</h1></body></html>
            """;
            final String CRLF = "\n\r"; // ascii 13, 10
            String response = "HTTP/1.1 200 OK" + CRLF + // Status line -> HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                              "Content-Length: " + html.getBytes().length + CRLF + // Header
                              CRLF +
                              html + // Body
                              CRLF + CRLF;
            outputStream.write(response.getBytes());

            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
