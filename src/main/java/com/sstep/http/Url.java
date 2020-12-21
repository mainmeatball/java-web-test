package com.sstep.http;

import com.sstep.http.exception.HttpParsingException;
import com.sstep.util.StringUtils;

import java.util.*;


/**
 * @author sstepanov
 */
public class Url {

    private List<String> path;
    private Map<String, String> parameters;
    private boolean root;
    private String file;

    public static Url root() {
        return new Url(Collections.emptyList(), null, true, new HashMap<>());
    }

    public Url(final List<String> path,
               final String file,
               final Map<String, String> parameters) {
        this(path, file, false, parameters);
    }

    public Url(final List<String> path,
               final String file,
               final boolean root,
               final Map<String, String> parameters) {
        this.path = path;
        this.parameters = parameters;
        this.file = file;
        this.root = root;
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
}
