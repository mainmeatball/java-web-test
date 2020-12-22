package com.sstep.http.parser;

import com.sstep.http.Url;
import com.sstep.http.exception.HttpParsingException;
import com.sstep.util.HttpUtilsKt;
import com.sstep.util.StringUtils;

import java.util.HashMap;
import java.util.List;


/**
 * @author sstepanov
 */
public class UrlParser {

    public static Url parse(final String pathWithParams) throws HttpParsingException {
        if (pathWithParams.isEmpty()) {
            throw new HttpParsingException("Invalid path and params string");
        }
        if (pathWithParams.charAt(0) != '/') {
            throw new HttpParsingException("Invalid path and params string");
        }
        if (pathWithParams.length() == 1 && pathWithParams.charAt(0) == '/') {
            return Url.root();
        }
        final String[] pathAndParams = pathWithParams.split("\\?");

        final List<String> path = List.of(pathAndParams[0].substring(1).split("/"));
        String file = null;

        if (path.size() > 0) {
            final String last = path.get(path.size() - 1);
            if (HttpUtilsKt.isValidFile(last)) {
                file = last;
            }
        }

        if (pathAndParams.length == 1) {
            return new Url(path, file, false, new HashMap<>());
        }

        final String params = pathAndParams[1];
        final HashMap<String, String> parameters = new HashMap<>();

        final String[] paramsPairs = params.split("&");
        for (final var pp : paramsPairs) {
            final String[] fullParam = pp.split("=");
            if (fullParam.length != 2) {
                throw new HttpParsingException("Invalid params string.");
            }
            final String key = fullParam[0];
            if (StringUtils.isEmpty(key)) {
                final String errorMessage = String.format("Key parameter is missing while parsing path parameters. Pair is: key=(%s), value(%s)", fullParam[0], fullParam[1]);
                throw new HttpParsingException(errorMessage);
            }
            final String value = fullParam[1];
            if (StringUtils.isEmpty(value)) {
                throw new HttpParsingException("Value parameter is missing while parsing path parameters.");
            }
            parameters.put(key, value);
        }
        return new Url(path, file, false, parameters);
    }
}
