package com.sstep.http;

/**
 * @author sstepanov
 */
public enum ResponseCode {
    RC_200("200 OK");

    private final String name;

    ResponseCode(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
