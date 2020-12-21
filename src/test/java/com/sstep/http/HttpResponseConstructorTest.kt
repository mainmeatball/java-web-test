package com.sstep.http

import com.sstep.http.enums.ConnectionType
import com.sstep.http.enums.ContentType
import com.sstep.http.enums.HttpVersion
import com.sstep.http.enums.ResponseCode
import com.sstep.http.parser.UrlParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


/**
 * @author sstepanov
 */
class HttpResponseConstructorTest {

    @Test
    fun `Construct GET HTTP response from request`() {
        val pathWithParams = "/path1/path2/path3/index.html?p1=v1&p2=v2&p3=v3"
        val url = UrlParser.parse(pathWithParams)
        val request = HttpRequest.constructGet(url)

        val response = HttpResponse(request)

        val fileContent = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<body>\n" +
            "<h1>Test Heading</h1>\n" +
            "<p>Test paragraph.</p>\n" +
            "</body>\n" +
            "</html>"

        assertEquals(HttpVersion.HTTP_1_1, response.httpVersion)
        assertEquals(ResponseCode.RC_200, response.responseCode)
        assertEquals(fileContent, response.fileContent)
        assertEquals(fileContent.length, response.contentLength)
        assertEquals(ConnectionType.CLOSED, response.connectionType)
        assertEquals(ContentType.TEXT_HTML, response.contentType)
    }
}