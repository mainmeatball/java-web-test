package com.sstep.http;

import com.sstep.http.controller.impl.HttpController;
import com.sstep.http.controller.WebController;
import com.sstep.http.entity.FileContent;
import com.sstep.http.enums.ConnectionType;
import com.sstep.http.enums.ContentType;
import com.sstep.http.enums.HttpVersion;
import com.sstep.http.enums.ResponseCode;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


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

    private final FileContent fileContent;

    private ConnectionType connectionType = ConnectionType.CLOSED;

    private ContentType contentType = ContentType.TEXT_HTML;

    public HttpResponse(final HttpRequest request) {
        httpVersion = request.getHttpVersion();
        date = LocalDateTime.now();
        responseCode = ResponseCode.RC_200;
        final Url url = request.getUrl();
        final WebController controller = HttpController.getInstance();
        fileContent = controller.getContent(url);
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

    public FileContent getFileContent() {
        return fileContent;
    }

    public LocalDateTime getLastModified() {
        return fileContent.getLastModified();
    }

    public String getLastModifiedFormatted() {
        final LocalDateTime lastModified = fileContent.getLastModified();
        return lastModified == null ? "" : lastModified.format(GMT_DATE_FORMATTER);
    }

    public String getLastModifiedHeader() {
        final LocalDateTime lastModified = fileContent.getLastModified();
        return lastModified == null ? "" : LAST_MODIFIED + lastModified.format(GMT_DATE_FORMATTER);
    }

    public int getContentLength() {
        return fileContent.getContentLength();
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public byte[] constructBytes() {
        return toString().getBytes();
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
                .append("Content-Length: ").append(getContentLength()).append('\n')
                .append("Connection: ").append(connectionType).append('\n')
                .append("Content-Type: ").append(contentType.getName()).append("\n\n")
                .append(fileContent.getContent()).append('\n');
        return sb.toString();
    }
}
