package com.apollo.music.data.entity;

import java.util.Map;

public interface EntityWithId {
    String getId();

    Map<String, Object> createFieldValueMap();
}
