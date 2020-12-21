package com.sstep.http;

import com.sstep.http.enums.ConnectionType;
import com.sstep.http.enums.ContentType;
import com.sstep.http.enums.HttpVersion;
import com.sstep.http.enums.ResponseCode;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Collectors;


/**
 * @author sstepanov
 */
public class HttpResponse {

    private final static DateTimeFormatter GMT_DATE_FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH).withZone(ZoneId.of("GMT"));

    private final static String LAST_MODIFIED = "Last-Modified: ";

    private final HttpVersion httpVersion;

    private final ResponseCode responseCode;

    private final LocalDateTime date;

    private final String server = "Java web server";

    private LocalDateTime lastModified;

    private String fileContent = "";

    private int contentLength;

    private ConnectionType connectionType = ConnectionType.CLOSED;

    private ContentType contentType = ContentType.TEXT_HTML;

    public HttpResponse(final HttpRequest request) {
        httpVersion = request.getHttpVersion();
        date = LocalDateTime.now();
        responseCode = ResponseCode.RC_200;
        lastModified = LocalDateTime.now();
        final String file = request.getUrl().getFile();
        if (file != null) {
            try {
                final URL resources = getClass().getClassLoader().getResource("static/" + file);
                if (resources == null) {
                    throw new IllegalArgumentException("The file in URL path is invalid.");
                }
                final Path path = Paths.get(resources.getFile());
                fileContent = Files.lines(path).collect(Collectors.joining("\n"));
                lastModified = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneId.of("GMT"));
                contentLength = fileContent.length();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getDateFormatted() {
        return date.format(GMT_DATE_FORMATTER);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public String getServer() {
        return server;
    }

    public String getFileContent() {
        return fileContent;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public String getLastModifiedFormatted() {
        return lastModified == null ? "" : lastModified.format(GMT_DATE_FORMATTER);
    }

    public String getLastModifiedHeader() {
        return lastModified == null ? "" : LAST_MODIFIED + lastModified.format(GMT_DATE_FORMATTER);
    }

    public int getContentLength() {
        return contentLength;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public String construct() {
        return toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(httpVersion).append(" ").append(responseCode).append('\n')
                .append("Date: ").append(getDateFormatted()).append('\n')
                .append("Server: ").append(server).append('\n')
                .append(getLastModifiedHeader()).append('\n')
                .append("Content-Length: ").append(contentLength).append('\n')
                .append("Connection: ").append(connectionType).append('\n')
                .append("Content-Type: ").append(contentType).append("\n\n")
                .append(fileContent).append('\n');
        return sb.toString();
    }
}
