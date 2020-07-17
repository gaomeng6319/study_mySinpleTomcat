package com.wying.test;

import com.wying.tomcat.HttpRequest;
import com.wying.tomcat.HttpResponse;
import com.wying.tomcat.HttpServlet;

import java.io.IOException;

/**
 * description:
 * date: 2020/7/14
 * author: gaom
 * version: 1.0
 */
public class TestServlet extends HttpServlet {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        System.out.println("TestServlet doGet............;..");
        String name= httpRequest.getParamter("name");
        httpResponse.write(" <html><body><h1>TestServlet  success!!!! name:"+name+"</h1></body></html>");

    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        System.out.println("TestServlet doPost..............");

    }
}
