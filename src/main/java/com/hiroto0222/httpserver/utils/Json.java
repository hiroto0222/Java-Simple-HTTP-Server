package com.hiroto0222.httpserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

/**
 * Json is a utility class for working with Json files
 */
public class Json {
    // Single instance of ObjectMapper
    private static final ObjectMapper myObjectMapper = defaultObjectMapper();

    // Configures ObjectMapper with settings
    private static ObjectMapper defaultObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        // Configure the ObjectMapper to ignore unknown properties during deserialization.
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om;
    }

    /**
     * Parses a JSON string and returns the corresponding JsonNode
     * @param jsonSrc path to json file
     */
    public static JsonNode parse(String jsonSrc) throws JsonProcessingException {
        return myObjectMapper.readTree(jsonSrc);
    }

    /**
     * Converts a JsonNode to an Object of specified type
     * @param node JsonNode to convert
     * @param c Type of Object to convert JsonNode into
     */
    public static <A> A fromJson(JsonNode node, Class<A> c) throws JsonProcessingException {
        return myObjectMapper.treeToValue(node, c);
    }

    /**
     * Converts an Object to a JsonNode
     * @param obj Object to convert
     */
    public static JsonNode toJson(Object obj) {
        return myObjectMapper.valueToTree(obj);
    }

    /**
     * Generates String representation of JsonNode
     * @param node JsonNode to stringify
     */
    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }

    /**
     * Generates String representation of JsonNode with pretty format
     * @param node JsonNode to stringify
     * @param pretty Set true for pretty formatting
     */
    public static String stringify(JsonNode node, boolean pretty) throws JsonProcessingException {
        return generateJson(node, pretty);
    }

    // Helper method to generate a JSON string from an object
    private static String generateJson(Object obj, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = myObjectMapper.writer();
        if (pretty) {
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(obj);
    }
}
