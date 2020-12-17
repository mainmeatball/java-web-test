package com.sstep.http.exception;

/**
 * @author sstepanov
 */
public class HttpParsingException extends Exception {

    public HttpParsingException() {
        super("Http is invalid");
    }

    public HttpParsingException(final String errorMessage) {
        super(errorMessage);
    }

    public HttpParsingException(final String errorMessage, final Throwable cause) {
        super(errorMessage, cause);
    }
}
