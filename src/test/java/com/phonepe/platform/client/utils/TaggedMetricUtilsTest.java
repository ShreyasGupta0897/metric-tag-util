package com.phonepe.platform.client.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

class TaggedMetricUtilsTest {

    @Test
    void getTaggedNameContainsDelimiter() {
        final String tagged = TaggedMetricUtils.getTaggedName("cpu.usage", Map.of("host", "h1"));
        assertTrue(tagged.contains(TaggedMetricUtils.DELIMITER));
    }

    @Test
    void getTaggedNameHandlesNullTags() {
        final String tagged = TaggedMetricUtils.getTaggedName("cpu.usage", null);
        assertTrue(TaggedMetricUtils.isTaggedMetric(tagged));
        assertTrue(TaggedMetricUtils.getTagsFromName(tagged).isEmpty());
    }

    @Test
    void getBaseNameRecoversOriginalName() {
        final String name = "cpu.usage";
        final String tagged = TaggedMetricUtils.getTaggedName(name, Map.of("host", "h1"));
        assertEquals(name, TaggedMetricUtils.getBaseName(tagged));
    }

    @Test
    void getTagsFromNameRecoversOriginalTags() {
        final Map<String, String> tags = Map.of("host", "h1", "az", "az-1");
        final String tagged = TaggedMetricUtils.getTaggedName("cpu.usage", tags);
        assertEquals(tags, TaggedMetricUtils.getTagsFromName(tagged));
    }

    @Test
    void isTaggedMetricFalseForPlainName() {
        assertFalse(TaggedMetricUtils.isTaggedMetric("cpu.usage"));
    }

    @Test
    void getTagsFromNameReturnsMutableEmptyMapForPlainName() {
        final Map<String, String> tags = TaggedMetricUtils.getTagsFromName("cpu.usage");
        assertTrue(tags.isEmpty());
        tags.put("host", "h1");
        assertEquals("h1", tags.get("host"));
    }

    @Test
    void getBaseNameDecodesPlainEncodedName() {
        final String encoded = HashUtil.encodeString("cpu.usage");
        assertEquals("cpu.usage", TaggedMetricUtils.getBaseName(encoded));
    }
}
