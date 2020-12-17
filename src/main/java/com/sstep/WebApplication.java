package com.sstep;

import com.sstep.servlet.TestServlet;


/**
 * @author sstepanov
 */
public class WebApplication {
    public static void main(final String[] args) {
        final TestServlet server = new TestServlet(8090);
        server.listen();
    }
}
