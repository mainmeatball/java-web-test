package com.sstep.http;

import com.sstep.http.enums.HttpMethod;
import com.sstep.http.enums.HttpVersion;


/**
 * @author sstepanov
 */
public class HttpRequest {

    private final HttpMethod httpMethod;

    private final Url url;

    private final HttpVersion version;

    public static HttpRequest constructGet(final Url url) {
        return new HttpRequest(HttpMethod.GET, url);
    }

    /**
     * Constructs HTTP/1.1 request.
     */
    public HttpRequest(final HttpMethod httpMethod,
                       final Url url) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.version = HttpVersion.HTTP_1_1;
    }

    public HttpRequest(final HttpMethod httpMethod,
                       final Url url,
                       final HttpVersion version) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.version = version;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Url getUrl() {
        return url;
    }

    public HttpVersion getHttpVersion() {
        return version;
    }
}
