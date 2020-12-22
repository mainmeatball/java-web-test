package com.sstep.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author sstepanov
 */
public class Url {

    private final List<String> path;
    private final Map<String, String> parameters;
    private final boolean root;
    private final String file;

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

    public String getJoinedPath() {
        return "/" + String.join("/", path);
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
