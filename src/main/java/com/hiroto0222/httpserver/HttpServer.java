package com.hiroto0222.httpserver;

import com.hiroto0222.httpserver.config.Configuration;
import com.hiroto0222.httpserver.config.ConfigurationManager;
import com.hiroto0222.httpserver.core.ServerListenerThread;

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
 *  - make multiple connections using threads
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

        // create TCP socket on thread
        ServerListenerThread serverListenerThread;
        try {
            serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
