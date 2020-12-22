package com.sstep.http.controller.impl;

import com.sstep.http.annotation.Get;


/**
 * @author sstepanov
 */
public class HttpController extends AbstractHttpController {

    private static volatile HttpController instance;

    public static HttpController getInstance() {
        HttpController localInstance = instance;
        if (localInstance == null) {
            synchronized (HttpController.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new HttpController();
                }
            }
        }
        return localInstance;
    }

    private HttpController() {
    }

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
