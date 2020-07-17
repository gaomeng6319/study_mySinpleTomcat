package com.wying.tomcat;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * date: 2020/7/14
 * author: gaom
 * version: 1.0
 */
public class ServletMappingConfig {

    public static List<ServletMapping> servletMappingList =new ArrayList<>();
    //简单处理 tomcat是从web.xml读取
    static{
        servletMappingList.add(new ServletMapping("testServlet","/testServlet","com.wying.test.TestServlet"));

    }

}
