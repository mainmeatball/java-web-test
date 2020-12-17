package com.sstep.http;

import com.sstep.http.exception.HttpParsingException;

import java.io.BufferedReader;
import java.io.IOException;


/**
 * @author sstepanov
 */
public class HttpRequest {

    private final HttpMethod httpMethod;

    private final Uri uri;

    private final HttpVersion version;

    public HttpRequest(final BufferedReader inputStream) throws IOException, HttpParsingException {
        if (!inputStream.ready()) {
            throw new IllegalArgumentException("Request is empty");
        }
        final String startString = inputStream.readLine();
        final String[] firstLine = startString.split(" ");
        if (firstLine.length != 3) {
            throw new HttpParsingException();
        }
        httpMethod = HttpMethod.valueOf(firstLine[0]);
        uri = new Uri(firstLine[1]);
        version = HttpVersion.parse(firstLine[2]);
//        while (inputStream.ready()) {
//
//        }
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Uri getUri() {
        return uri;
    }

    public HttpVersion getHttpVersion() {
        return version;
    }
}
