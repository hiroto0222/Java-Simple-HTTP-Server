package com.hiroto0222.httpserver;

import com.hiroto0222.httpserver.config.Configuration;
import com.hiroto0222.httpserver.config.ConfigurationManager;
import com.hiroto0222.httpserver.core.ServerListenerThread;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {
        LOGGER.info("Server starting...");

        // load configurations
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        LOGGER.info("Using Port: {}", conf.getPort());
        LOGGER.info("Using WebRoot: {}", conf.getWebroot());

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
