package com.sstep.servlet;

import com.sstep.http.HttpRequest;
import com.sstep.http.HttpResponse;
import com.sstep.http.exception.HttpParsingException;
import com.sstep.http.parser.HttpRequestParser;

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

                    final HttpRequest request = HttpRequestParser.parse(in);
                    final HttpResponse response = new HttpResponse(request);

                    out.write(response.construct());
                    out.flush();
                } catch (final HttpParsingException e) {
                    e.printStackTrace();
                    return;
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}