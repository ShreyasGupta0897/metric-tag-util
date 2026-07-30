package com.phonepe.platform.client.utils;

import static com.phonepe.platform.client.utils.HashUtil.decodeMap;
import static com.phonepe.platform.client.utils.HashUtil.decodeString;
import static com.phonepe.platform.client.utils.HashUtil.encodeMap;
import static com.phonepe.platform.client.utils.HashUtil.encodeString;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TaggedMetricUtils {

    public static final String DELIMITER = "~";
    public static final String DELIMITER_ESCAPED_REGEX = Pattern.quote(DELIMITER);

    public static String getTaggedName(final String name,
                                       @Nullable final Map<String, String> tags) {

        final String encodedMetricName = encodeString(name);

        final Map<String, String> tagsOrEmpty = tags != null
                                ? tags
                                : Map.of();

        return encodedMetricName + DELIMITER + encodeMap(tagsOrEmpty);
    }

    public static String getBaseName(final String name) {
        if (isTaggedMetric(name)) {
            return decodeString(name.split(DELIMITER_ESCAPED_REGEX)[0]);
        }
        return decodeString(name);
    }

    public static boolean isTaggedMetric(final String name) {
        return name.contains(DELIMITER);
    }

    public static Map<String, String> getTagsFromName(final String name) {
        if (isTaggedMetric(name)) {
            return decodeMap(name.split(DELIMITER_ESCAPED_REGEX)[1]);
        }
        // The map returned here should be mutable. Generally tags are added upon.
        return Maps.newHashMap();
    }
}



