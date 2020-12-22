package com.sstep.http.entity;

import java.time.LocalDateTime;


/**
 * @author sstepanov
 */
public class FileContent {

    private final String content;

    private final LocalDateTime lastModified;

    public FileContent(final String content,
                       final LocalDateTime lastModified) {
        this.content = content;
        this.lastModified = lastModified;
    }

    public FileContent(final String content) {
        this.content = content;
        this.lastModified = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public int getContentLength() {
        return content.length();
    }
}
