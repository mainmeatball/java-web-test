package com.sstep.servlet;

import java.io.IOException;


/**
 * @author sstepanov
 */
public interface ConnectionServlet {
    void listen() throws InterruptedException, IOException;
}
