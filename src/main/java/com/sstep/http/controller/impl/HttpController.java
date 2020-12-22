package com.sstep.http.controller.impl;

import com.sstep.http.annotation.Get;


/**
 * @author sstepanov
 */
public class HttpController extends AbstractHttpController {

    @Get("/hello")
    public String hello() {
        return "HELLO!";
    }

    @Get()
    public String home() {
        return "home.html";
    }

    @Get("/home")
    public String homePage() {
        return home();
    }

    @Get("/index")
    public String index() {
        return "index.html";
    }
}
