package com.phonepe.platform.client.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HashUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String encodeMap(final Map<String, String> map) {
        try {
            return base64Encode(mapper.writeValueAsBytes(map));
        } catch (final JsonProcessingException e) {
            throw new RuntimeException("Error while encoding tags", e);
        }
    }

    public static String encodeString(final String data) {
        return base64Encode(data.getBytes());
    }

    public static <T> Map<String, T> decodeMap(final String base64EncodedMap) {
        try {
            return mapper.readValue(base64Decode(base64EncodedMap), new TypeReference<>() {
            });
        } catch (final IOException e) {
            throw new RuntimeException("Error while decoding tags", e);
        }
    }

    public static boolean isBase64(final String input) {
        if (Strings.isNullOrEmpty(input)) {
            return false;
        }
        try {
            Base64.getDecoder().decode(input);
            return true;
        } catch (final IllegalArgumentException e) {
            return false;
        }
    }

    public static String decodeString(final String base64EncodedString) {
        if(!isBase64(base64EncodedString)) {
            return base64EncodedString;
        }
        final byte[] decodedBytes = base64Decode(base64EncodedString);
        return new String(decodedBytes);
    }

    private static String base64Encode(final byte[] data) {
        return Base64.getEncoder()
                .encodeToString(data);
    }

    private static byte[] base64Decode(final String data) {
        return Base64.getDecoder()
                .decode(data);
    }
}