package com.wying.tomcat;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * description: 输入流解析url和method封装成简易httprequest
 * date: 2020/7/14
 * author: gaom
 * version: 1.0
 */
public class HttpRequest {
    private String url;
    private String method;
    private String paramStr;
    private Map<String,String> paramMap=new HashMap<>();

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }






    public HttpRequest(InputStream inputStream) throws IOException {


        String httpRequestStr ="";

        byte[] bytes=new byte[1024];
        int length =0;
        if((length=inputStream.read(bytes)) >0){
            httpRequestStr=new String(bytes,0,length);
        }
        System.out.println("HttpRequest inputStream:"+httpRequestStr);
        String httpHead = httpRequestStr.split("\n")[0];
        //解析出http请求的url 和method
        //GET /testServlet?name=123 HTTP/1.1
        method=httpHead.split("\\s")[0];
        String httpUrl=httpHead.split("\\s")[1];

        //解析get传参数据
       if(httpUrl.indexOf("?")>=0){
          String[] httpUrls= httpUrl.split("\\?");
           url=httpUrls[0];
           paramStr=httpUrls[1];
           setParams();
       }else{
           url=httpUrl;
       }
        System.out.println("HttpRequest url:"+url+" method:"+method);

    }
    //解析参数存入map
    private void setParams(){
        System.out.println("解析参数存入map  this.paramStr:"+this.paramStr);
        String[] params = this.paramStr.split("&");

        for (String param : params) {
            String[] keyValue = param.split("=");
            paramMap.put(keyValue[0], keyValue[1]);
        }
    }
    //获取参数
    public String getParamter(String paramName){
        return paramMap.get(paramName);
    }



}
