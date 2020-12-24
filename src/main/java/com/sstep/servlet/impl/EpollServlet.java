package com.sstep.servlet.impl;

import com.sstep.http.HttpRequest;
import com.sstep.http.HttpResponse;
import com.sstep.http.exception.HttpParsingException;
import com.sstep.http.parser.HttpRequestParser;
import com.sstep.servlet.ConnectionServlet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 * @author sstepanov
 */
public class EpollServlet implements ConnectionServlet {

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private final int port;

    public EpollServlet(final int port) {
        this.port = port;
    }

    public void listen() throws IOException {
        try (final Selector selector = Selector.open();
             final ServerSocketChannel socket = ServerSocketChannel.open()) {

            socket.configureBlocking(false);

            final InetSocketAddress addr = new InetSocketAddress("localhost", port);
            socket.bind(addr);
            socket.register(selector, SelectionKey.OP_ACCEPT, null);

            while (true) {
                selector.select();

                final Set<SelectionKey> socketKeys = selector.selectedKeys();
                final Iterator<SelectionKey> iter = socketKeys.iterator();

                while (iter.hasNext()) {
                    final SelectionKey connection = iter.next();

                    if (!connection.isValid()) {
                        continue;
                    }

                    if (connection.isAcceptable()) {
                        accept(socket, selector);
                    } else if (connection.isReadable()) {
                        readAndAnswer(connection);
                    }
                    iter.remove();
                }
            }
        } catch (final HttpParsingException e) {
            e.printStackTrace();
        }
    }

    private void accept(final ServerSocketChannel socket, final Selector selector) throws IOException {
        final SocketChannel client = socket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        log("Connection Accepted: " + client.getLocalAddress());
    }

    private void readAndAnswer(final SelectionKey connection) throws IOException, HttpParsingException {
        final SocketChannel client = (SocketChannel) connection.channel();
        final ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
        final int readSignal = client.read(buffer);
        if (readSignal == -1) {
            client.close();
            return;
        }
        final byte[] requestBytes = buffer.array();
        final HttpRequest request = HttpRequestParser.parse(requestBytes);
        final HttpResponse httpResponse = new HttpResponse(request);
        log("Reading: \n" + new String(requestBytes));

        final byte[] response = httpResponse.constructBytes();
        final ByteBuffer writeBuffer = ByteBuffer.allocate(response.length);
        writeBuffer.put(response);
        writeBuffer.flip();
        client.write(writeBuffer);
        log("Writing: \n" + new String(response));
    }

    private void log(final String msg) {
        System.out.println(msg);
    }
}