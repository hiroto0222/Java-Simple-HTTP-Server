package com.hiroto0222.httpserver.core;

import com.hiroto0222.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread{
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    private int port;
    private String webRoot;
    ServerSocket serverSocket;

    public ServerListenerThread(int port, String webRoot) throws IOException {
        this.port = port;
        this.webRoot = webRoot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        try {
            Socket socket = serverSocket.accept();

            LOGGER.info(" * Connection Accepted: {}", socket.getInetAddress());

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
