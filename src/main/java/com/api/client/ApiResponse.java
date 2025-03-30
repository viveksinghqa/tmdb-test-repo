package com.api.client;

import com.fasterxml.jackson.databind.JsonNode;

public class ApiResponse {
    private final int statusCode;
    private final JsonNode body;

    public ApiResponse(int statusCode, JsonNode body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public JsonNode getBody() {
        return body;
    }
}
