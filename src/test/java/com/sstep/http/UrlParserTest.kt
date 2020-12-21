package com.sstep.http

import com.sstep.http.exception.HttpParsingException
import com.sstep.http.parser.UrlParser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * @author sstepanov
 */
class UrlParserTest {

    @Test
    fun `Parse path without file with parameters successfully`() {
        val pathWithParams = "/path1/path2/path3?p1=v1&p2=v2&p3=v3"

        val url = UrlParser.parse(pathWithParams)

        assertEquals(3, url.path.size)
        assertEquals("path1", url.path[0])
        assertEquals("path2", url.path[1])
        assertEquals("path3", url.path[2])

        assertEquals(3, url.parameters.size)
        assertEquals("v1", url.parameters["p1"])
        assertEquals("v2", url.parameters["p2"])
        assertEquals("v3", url.parameters["p3"])

        assertFalse(url.isRoot)
    }

    @Test
    fun `Parse path with file with parameters successfully`() {
        val pathWithParams = "/path1/path2/path3/index.html?p1=v1&p2=v2&p3=v3"

        val url = UrlParser.parse(pathWithParams)

        assertEquals(4, url.path.size)
        assertEquals("path1", url.path[0])
        assertEquals("path2", url.path[1])
        assertEquals("path3", url.path[2])
        assertEquals("index.html", url.path[3])

        assertEquals(3, url.parameters.size)
        assertEquals("v1", url.parameters["p1"])
        assertEquals("v2", url.parameters["p2"])
        assertEquals("v3", url.parameters["p3"])

        assertFalse(url.isRoot)

        assertEquals("index.html", url.file)
    }

    @Test
    fun `Parse root path successfully`() {
        val rootPath = "/"

        val url = UrlParser.parse(rootPath)

        assertEquals(0, url.path.size)
        assertEquals(0, url.parameters.size)
        assertTrue(url.isRoot)
    }

    @Test
    fun `Parse path without params successfully`() {
        val pathWithoutParams = "/path1/path2"

        val url = UrlParser.parse(pathWithoutParams)

        assertEquals(2, url.path.size)
        assertEquals("path1", url.path[0])
        assertEquals("path2", url.path[1])
        assertEquals(0, url.parameters.size)
        assertFalse(url.isRoot)
    }

    @Test
    fun `Parse empty path throws exception`() {
        val pathWithoutParams = ""

        assertThrows<HttpParsingException> {
            UrlParser.parse(pathWithoutParams)
        }
    }
}