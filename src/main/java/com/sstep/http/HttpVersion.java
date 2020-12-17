package com.sstep.http;

import com.sstep.http.exception.HttpParsingException;


/**
 * @author sstepanov
 */
public enum HttpVersion {
    HTTP_1_1,
    HTTP_2;

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
