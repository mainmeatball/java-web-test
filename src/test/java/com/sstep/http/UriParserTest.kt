package com.sstep.http

import com.sstep.http.exception.HttpParsingException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * @author sstepanov
 */
class UriParserTest {

    @Test
    fun `Parse path without file with parameters successfully`() {
        val pathWithParams = "/path1/path2/path3?p1=v1&p2=v2&p3=v3"

        val uri = Uri(pathWithParams)

        assertEquals(3, uri.path.size)
        assertEquals("path1", uri.path[0])
        assertEquals("path2", uri.path[1])
        assertEquals("path3", uri.path[2])

        assertEquals(3, uri.parameters.size)
        assertEquals("v1", uri.parameters["p1"])
        assertEquals("v2", uri.parameters["p2"])
        assertEquals("v3", uri.parameters["p3"])

        assertFalse(uri.isRoot)
    }

    @Test
    fun `Parse path with file with parameters successfully`() {
        val pathWithParams = "/path1/path2/path3/index.html?p1=v1&p2=v2&p3=v3"

        val uri = Uri(pathWithParams)

        assertEquals(4, uri.path.size)
        assertEquals("path1", uri.path[0])
        assertEquals("path2", uri.path[1])
        assertEquals("path3", uri.path[2])
        assertEquals("index.html", uri.path[3])

        assertEquals(3, uri.parameters.size)
        assertEquals("v1", uri.parameters["p1"])
        assertEquals("v2", uri.parameters["p2"])
        assertEquals("v3", uri.parameters["p3"])

        assertFalse(uri.isRoot)

        assertEquals("index.html", uri.file)
    }

    @Test
    fun `Parse root path successfully`() {
        val rootPath = "/"

        val uri = Uri(rootPath)

        assertEquals(0, uri.path.size)
        assertEquals(0, uri.parameters.size)
        assertTrue(uri.isRoot)
    }

    @Test
    fun `Parse path without params successfully`() {
        val pathWithoutParams = "/path1/path2"

        val uri = Uri(pathWithoutParams)

        assertEquals(2, uri.path.size)
        assertEquals("path1", uri.path[0])
        assertEquals("path2", uri.path[1])
        assertEquals(0, uri.parameters.size)
        assertFalse(uri.isRoot)
    }

    @Test
    fun `Parse empty path throws exception`() {
        val pathWithoutParams = ""

        assertThrows<HttpParsingException> {
            Uri(pathWithoutParams)
        }
    }
}