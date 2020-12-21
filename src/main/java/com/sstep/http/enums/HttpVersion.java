package com.sstep.http.enums;

import com.sstep.http.exception.HttpParsingException;


/**
 * @author sstepanov
 */
public enum HttpVersion {
    HTTP_1_1("HTTP/1.1"),
    HTTP_2("HTTP/2");

    private final String name;

    HttpVersion(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public static HttpVersion parse(final String version) throws HttpParsingException {
        if (version.equals("HTTP/1.1")) {
            return HTTP_1_1;
        } else if (version.equals("HTTP/2")) {
            return HTTP_2;
        } else {
            throw new HttpParsingException("HTTP version is invalid");
        }
    }
}
