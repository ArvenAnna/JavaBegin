package com.main.acad.servlet;

import com.main.acad.annotation.Mapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static HttpServletResponse response;
    public static HttpServletRequest request;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        String url = request.getRequestURL().toString();

        try {
            url = url.substring(url.indexOf("4") + 2);
            response.getWriter().write(invokeController(url, request, response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String invokeController(String url, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Class<?>> classes = getClasses("com.main.acad.controller");
        String urlFull = "[@com.main.acad.annotation.Mapping(url=" + url + ")]";
        for (Class<?> e : classes) {
            if (Arrays.toString(e.getAnnotations()).equals(urlFull)) {
                String s = "com.main.acad.controller." + e.getSimpleName();
                Class clazz = Class.forName(s);
                Object o = clazz.newInstance();
                Class c = o.getClass();
                Class[] paramTypes = new Class[]{HttpServletRequest.class, HttpServletResponse.class};
                Method m = c.getMethod("returnResponce", paramTypes);
                Object[] args = new Object[]{request, response};
                String d = (String) m.invoke(o, args);
                return d;
            }
        }
        return null;
    }

    public static List<Class<?>> getClasses(String pack) throws Exception {
        ClassLoader cl = Mapping.class.getClassLoader();
        String packageDir = pack.replaceAll("[.]", "/");
        List<Class<?>> classes = new ArrayList<>();
        URL upackage = cl.getResource(packageDir);
        InputStream in = (InputStream) upackage.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = null;
        while ((line = br.readLine()) != null) {
            if (line.endsWith(".class"))
                classes.add(Class.forName(pack + "." + line.substring(0, line.lastIndexOf('.'))));
        }
        return classes;
    }
}