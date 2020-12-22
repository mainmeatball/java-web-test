package com.sstep.servlet;

import com.sstep.http.HttpRequest;
import com.sstep.http.HttpResponse;
import com.sstep.http.exception.HttpParsingException;
import com.sstep.http.parser.HttpRequestParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * @author sstepanov
 */
public class TestServlet {

    private final int port;

    public TestServlet(final int port) {
        this.port = port;
    }

    public void listen() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        boolean running = true;
        try (final ServerSocket serverSocket = new ServerSocket(port)) {
            while (running) {
                try {
                    final Socket clientSocket = serverSocket.accept();
                    executorService.submit(() -> acceptConnection(clientSocket));
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    private void acceptConnection(final Socket clientSocket) {
        try(final BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            System.out.println("The Client " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " is connected");

            final HttpRequest request = HttpRequestParser.parse(in);
            final HttpResponse response = new HttpResponse(request);

            out.write(response.construct());
            out.flush();
        } catch (final IOException | HttpParsingException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }
}