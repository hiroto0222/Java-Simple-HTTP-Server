package com.hiroto0222.httpserver;

import com.hiroto0222.httpserver.config.Configuration;
import com.hiroto0222.httpserver.config.ConfigurationManager;

/**
 * Driver Class for HTTP Server
 * 1. needs to be listening to at least 1 port
 *  - unencrypted traffic on 80
 *  -> read configuration files
 * 2. make TCP socket connection on port with client
 * 3. read request messages following HTTP protocols
 * 4. open/read files from FS based on request
 * 5. write response message following HTTP protocols
 * 6. send response to client
 * 7. close TCP socket connection on port
 */
public class HttpServer {
    public static void main(String[] args) {
        System.out.println("Server starting...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("Using Port: " + conf.getPort());
        System.out.println("Using WebRoot: " + conf.getWebroot());
    }
}
