package com.wying.tomcat;

import java.io.IOException;
import java.io.OutputStream;

/**
 * description:简单httpresponse 直接出出文本
 * date: 2020/7/14
 * author: gaom
 * version: 1.0
 */
public class HttpResponse {
    private OutputStream outputStream;

    public HttpResponse(OutputStream outputStream){
        this.outputStream = outputStream;
    }

    public void write(String content)throws IOException {
        StringBuffer httpResponse = new StringBuffer();
        httpResponse.append("HTTP/1.1 200 OK\n")
                .append("Content-Type: text/html\n")
                .append("\r\n");

        httpResponse.append(content);
        outputStream.write(httpResponse.toString().getBytes());
        outputStream.close();
    }

}
