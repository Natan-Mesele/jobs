package com.example.jobs.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommaSeparatedSkillsDeserializer extends JsonDeserializer<List<String>> {

    @Override
    public List<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String value = jp.getText();  // Get the string value from the JSON field

        // If the value is not empty, split it by commas and trim each element to remove spaces
        if (value != null && !value.isEmpty()) {
            return Arrays.stream(value.split(","))
                    .map(String::trim)  // Trim spaces
                    .collect(Collectors.toList());
        }
        return null;  // Return null if the value is empty or null
    }
}
