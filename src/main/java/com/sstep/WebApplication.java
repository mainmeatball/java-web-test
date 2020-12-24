package com.sstep;

import com.sstep.servlet.ConnectionServlet;
import com.sstep.servlet.impl.EpollServlet;

import java.io.IOException;


/**
 * @author sstepanov
 */
public class WebApplication {
    public static void main(final String[] args) throws InterruptedException, IOException {
        final ConnectionServlet server = new EpollServlet(8090);
        server.listen();
    }
}
