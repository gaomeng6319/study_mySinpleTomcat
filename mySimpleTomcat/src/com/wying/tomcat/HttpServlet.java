package com.wying.tomcat;

import java.io.IOException;

/**
 * description:
 * date: 2020/7/14
 * author: gaom
 * version: 1.0
 */
public abstract class HttpServlet {
    public abstract void doGet(HttpRequest httpRequest,HttpResponse httpResponse) throws IOException;
    public abstract void doPost(HttpRequest httpRequest,HttpResponse httpResponse) throws IOException;
    public void service(HttpRequest httpRequest,HttpResponse httpResponse) throws IOException{
        if(httpRequest.getMethod().equalsIgnoreCase("POST")){
            doPost(httpRequest,httpResponse);
        }else if(httpRequest.getMethod().equalsIgnoreCase("GET")){
            doGet(httpRequest,httpResponse);
        }
    }

}
