package com.sstep.servlet;

import com.sstep.http.HttpRequest;
import com.sstep.http.exception.HttpParsingException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author sstepanov
 */
public class TestServlet {

    private final int port;

    public TestServlet(final int port) {
        this.port = port;
    }

    public void listen() {

        try (final ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (final Socket clientSocket = serverSocket.accept();
                     final BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

                    System.out.println("The Client " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " is connected");

                    final HttpRequest request = new HttpRequest(in);

                    out.write("HTTP/1.1 200 OK\n" +
                            "Date: Mon, 27 Jul 2009 12:28:53 GMT\n" +
                            "Server: Apache/2.2.14 (Win32)\n" +
                            "Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT\n" +
                            "Content-Length: 88\n" +
                            "Connection: Closed\n" +
                            "Content-Type: text/html\n" +
                            "\n" +
                            "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n" +
                            "<html>\n" +
                            "<body>\n" +
                            "<h1>Hello, World!</h1>\n" +
                            "</body>\n" +
                            "</html>" +
                            "\n");
                    out.flush();
                } catch (final HttpParsingException e) {
                    e.printStackTrace();
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}