package com.sstep.servlet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


/**
 * @author sstepanov
 */
public class Test {
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public void start(final int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();

        try (final BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
             final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
//            final String s = in.lines().collect(Collectors.joining());
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
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public void stop() throws IOException {
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(final String[] args) throws IOException {
        final Test server = new Test();
        server.start(8090);
    }
}
