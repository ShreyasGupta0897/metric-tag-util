package com.phonepe.platform.client.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

class HashUtilTest {

    @Test
    void encodeAndDecodeStringRoundTrips() {
        final String original = "my.metric.name";
        final String encoded = HashUtil.encodeString(original);
        assertEquals(original, HashUtil.decodeString(encoded));
    }

    @Test
    void encodeAndDecodeMapRoundTrips() {
        final Map<String, String> tags = Map.of("host", "server-1", "region", "in-west");
        final String encoded = HashUtil.encodeMap(tags);
        final Map<String, String> decoded = HashUtil.decodeMap(encoded);
        assertEquals(tags, decoded);
    }

    @Test
    void encodeAndDecodeEmptyMapRoundTrips() {
        final String encoded = HashUtil.encodeMap(Map.of());
        final Map<String, String> decoded = HashUtil.decodeMap(encoded);
        assertTrue(decoded.isEmpty());
    }

    @Test
    void isBase64ReturnsTrueForEncodedString() {
        final String encoded = HashUtil.encodeString("hello");
        assertTrue(HashUtil.isBase64(encoded));
    }

    @Test
    void isBase64ReturnsFalseForNullOrEmpty() {
        assertFalse(HashUtil.isBase64(null));
        assertFalse(HashUtil.isBase64(""));
    }

    @Test
    void isBase64ReturnsFalseForInvalidInput() {
        assertFalse(HashUtil.isBase64("not valid base64 !!!"));
    }

    @Test
    void decodeStringReturnsInputWhenNotBase64() {
        final String plain = "plain string with spaces !!!";
        assertEquals(plain, HashUtil.decodeString(plain));
    }
}
