package com.apollo.music.data.filter;

import org.apache.logging.log4j.util.Strings;

public class ContentManagerFilter {
    private String id;

    private String name;

    public ContentManagerFilter() {
    }

    public ContentManagerFilter(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return Strings.trimToNull(id);
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return Strings.trimToNull(name);
    }

    public void setName(final String name) {
        this.name = name;
    }
}
