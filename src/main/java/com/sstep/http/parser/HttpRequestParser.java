package com.sstep.http.parser;

import com.sstep.http.HttpRequest;
import com.sstep.http.Url;
import com.sstep.http.enums.HttpMethod;
import com.sstep.http.enums.HttpVersion;
import com.sstep.http.exception.HttpParsingException;

import java.io.BufferedReader;
import java.io.IOException;


/**
 * @author sstepanov
 */
public class HttpRequestParser {

    public static HttpRequest parse(final BufferedReader inputStream) throws IOException, HttpParsingException {
        if (!inputStream.ready()) {
            throw new IllegalArgumentException("Request is empty");
        }
        final String startString = inputStream.readLine();
        final String[] firstLine = startString.split(" ");
        if (firstLine.length != 3) {
            throw new HttpParsingException();
        }
        final HttpMethod httpMethod = HttpMethod.valueOf(firstLine[0]);
        final Url url = UrlParser.parse(firstLine[1]);
        final HttpVersion version = HttpVersion.parse(firstLine[2]);
        return new HttpRequest(httpMethod, url, version);
    }
}
