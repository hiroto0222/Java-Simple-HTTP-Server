package com.hiroto0222.httpserver.core;

import com.hiroto0222.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    private final Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream(); OutputStream outputStream = socket.getOutputStream()) {
            // read request
            int _byte;
            while ((_byte = inputStream.read()) >= 0) {
                System.out.print((char)_byte);
            }

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

            LOGGER.info("Connection processing finished.");
        } catch (IOException e) {
            LOGGER.error("Problem with communication, ", e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException _) {}
            }
        }
    }
}
