package com.sstep.http.enums;

/**
 * @author sstepanov
 */
public enum ContentType {
    TEXT_HTML("text/html");

    ContentType(final String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return name;
    }
}
