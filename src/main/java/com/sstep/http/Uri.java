package com.sstep.http;

import com.sstep.http.exception.HttpParsingException;
import com.sstep.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author sstepanov
 */
public class Uri {

    private static final List<String> ALLOWED_FILE_EXTENSIONS = new ArrayList<>();

    static {
        ALLOWED_FILE_EXTENSIONS.add("html");
        ALLOWED_FILE_EXTENSIONS.add("xml");
    }

    private List<String> path = new ArrayList<>();
    private final Map<String, String> parameters = new HashMap<>();
    private boolean root = false;
    private String file = null;

    public Uri(final String pathWithParams) throws HttpParsingException {
        if (pathWithParams.isEmpty()) {
            throw new HttpParsingException("Invalid path and params string");
        }
        if (pathWithParams.charAt(0) != '/') {
            throw new HttpParsingException("Invalid path and params string");
        }
        if (pathWithParams.length() == 1 && pathWithParams.charAt(0) == '/') {
            root = true;
            return;
        }
        final String[] pathAndParams = pathWithParams.split("\\?");

        path = List.of(pathAndParams[0].substring(1).split("/"));

        if (path.size() > 0) {
            final String last = path.get(path.size() - 1);
            if (isValidFile(last)) {
                file = last;
            }
        }

        if (pathAndParams.length == 1) {
            return;
        }

        final String params = pathAndParams[1];

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
    }

    public List<String> getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public boolean isRoot() {
        return root;
    }

    public String getFile() {
        return file;
    }

    private static boolean isValidFile(final String file) {
        if (file.indexOf('.') == -1) {
            return false;
        }
        final String[] fileWithExt = file.split("\\.");
        if (fileWithExt.length != 2) {
            return false;
        }
        final String ext = fileWithExt[1];
        return ALLOWED_FILE_EXTENSIONS.contains(ext);
    }
}
