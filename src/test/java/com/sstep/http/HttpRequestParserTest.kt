package com.sstep.http

import com.sstep.http.enums.HttpMethod
import com.sstep.http.enums.HttpVersion
import com.sstep.http.parser.HttpRequestParser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.StringReader


/**
 * @author sstepanov
 */
class HttpRequestParserTest {

    @Test
    fun `Parse GET HTTP request`() {
        val pathWithParams = "GET /path1/path2/path3?p1=v1&p2=v2&p3=v3 HTTP/1.1"
        val reader = BufferedReader(StringReader(pathWithParams))

        val request = HttpRequestParser.parse(reader)

        assertEquals(HttpMethod.GET, request.httpMethod)
        assertNotNull(request.url)
        assertEquals(HttpVersion.HTTP_1_1, request.httpVersion)
    }
}