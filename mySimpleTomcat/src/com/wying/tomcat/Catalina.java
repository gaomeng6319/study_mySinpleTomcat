package com.wying.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * description:tomcat启动类
 * date: 2020/7/14
 * author: gaom
 * version: 1.0
 */
public class Catalina {
    private int port=8088;
    private Map<String,String> urlServletMap =new HashMap<String,String>();
    public Catalina(int port){
        this.port=port;
    }

    public void start() {
//        初始化URL与对应处理的servlet的关系
        initServletMapping();

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("tomcat start port:"+port);

            while (true) {
                System.out.println("开始监听请求....");
                //监听请求
                Socket socket = serverSocket.accept();
                System.out.println("收到请求.....");
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                //封装httpRequest
                HttpRequest  httpRequest = new HttpRequest(inputStream);
                //封装httpResponse
                HttpResponse httpResponse = new HttpResponse(outputStream);
                System.out.println("request httpRequestUrl :"+httpRequest .getUrl());
                //分发器
                dispatch(httpRequest, httpResponse);
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != serverSocket) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
    //将url和对应的servlet类存入map
    private void initServletMapping(){
        for (ServletMapping servletMapping : ServletMappingConfig.servletMappingList) {
            urlServletMap.put(servletMapping.getUrl(), servletMapping.getClazz());
        }
    }
    public void dispatch(HttpRequest  httpRequest,HttpResponse httpResponse) throws IOException {
        //根据url找到servlet类
        if("/favicon.ico".equals(httpRequest.getUrl())){
            System.out.println("浏览器获取图标请求 暂时忽略");
            return;
        }
        String clazz =urlServletMap.get( httpRequest.getUrl());
        if(clazz==null){
            httpResponse.write(" error url:"+httpRequest.getUrl()+" not match Servlet");
            return;
        }

        try{
            //通过反射机制创建对应的servlet对象
            Class<HttpServlet> httpServletClass =(Class<HttpServlet>) Class.forName(clazz);
            HttpServlet  httpServlet=  httpServletClass.newInstance();

            httpServlet.service(httpRequest,httpResponse);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        Catalina catalina= new Catalina(8088);
        catalina.start();
    }

}
