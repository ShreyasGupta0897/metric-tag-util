# metric-tag-util

A small Java utility for encoding metric names together with their tags into a single string, and decoding them back.

## Requirements

- Java 17+
- Maven

## Build

```bash
mvn clean install
```

## Usage

```java
import com.phonepe.platform.client.utils.TaggedMetricUtils;
import java.util.Map;

String tagged = TaggedMetricUtils.getTaggedName("http.requests", Map.of("method", "GET"));

String name = TaggedMetricUtils.getBaseName(tagged);
Map<String, String> tags = TaggedMetricUtils.getTagsFromName(tagged);
```
