package com.hiroto0222.httpserver.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.hiroto0222.httpserver.utils.Json;

import java.io.FileReader;
import java.io.IOException;

/**
 * ConfigurationManager is a utility class for reading configurations
 */
public class ConfigurationManager {
    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if (myConfigurationManager == null) {
            myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;
    }

    /**
     * Used to load a Json configuration file for the http server by the path provided
     * @param filePath path to the Json configuration file
     */
    public void loadConfigurationFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        // try-with-resources statement to ensure FileReader is closed properly
        try (FileReader fileReader = new FileReader(filePath)) {
            int i;
            while ((i = fileReader.read()) != -1) {
                sb.append((char)i);
            }

            JsonNode conf = Json.parse(sb.toString());
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }
    }

    /**
     * Returns the current loaded Configuration
     */
    public Configuration getCurrentConfiguration() {
        if (myCurrentConfiguration == null) {
            throw new HttpConfigurationException("No current configuration set.");
        }
        return myCurrentConfiguration;
    }
}
