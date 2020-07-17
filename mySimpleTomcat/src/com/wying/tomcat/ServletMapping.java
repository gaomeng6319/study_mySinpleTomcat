package com.wying.tomcat;

/**
 * description:
 * date: 2020/7/14
 * author: gaom
 * version: 1.0
 */
public class ServletMapping {
    private String servletName;
    private String url;
    private String clazz;

    public ServletMapping(String servletName, String url, String clazz){
        this.servletName=servletName;
        this.url=url;
        this.clazz=clazz;
    }

    public String getServletName() {
        return servletName;
    }
    public void setServletName(String servletName) {
        this.servletName = servletName;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getClazz() {
        return clazz;
    }
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

}
