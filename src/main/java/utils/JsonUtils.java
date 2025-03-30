package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode parseJson(String json) throws Exception {
        return objectMapper.readTree(json);
    }

    public static boolean validateJsonField(JsonNode json, String field) {
        return json.has(field) && !json.get(field).asText().isEmpty();
    }
}
