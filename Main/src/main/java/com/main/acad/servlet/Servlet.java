package com.main.acad.servlet;

import com.main.acad.annotation.MappingMethod;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getRequestURL().toString();

            url = url.substring(url.indexOf("4") + 2);
        try {
            invokeController(url, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void invokeController(String url, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Class<?>> classes = getClasses("com.main.acad.controller");

        for (Class<?> s : classes) {
            String string = getMethod(s, url);
            Class clazz = Class.forName(s.getName());
            Object o = clazz.newInstance();
            Class c = o.getClass();
            Class[] paramTypes = new Class[]{HttpServletRequest.class, HttpServletResponse.class};
            Method m = c.getMethod(string, paramTypes);
            Object[] args = new Object[]{request, response};
            response.getWriter().write((String) m.invoke(o, args));
        }
    }

    public static List<Class<?>> getClasses(String pack) throws Exception {
        ClassLoader cl = MappingMethod.class.getClassLoader();
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

    public static String getMethod(Class cl, String url) {
        Method[] method = cl.getMethods();
        String m = null;
        for (Method md : method) {
            if (md.isAnnotationPresent(MappingMethod.class)) {
                if (Arrays.toString(md.getAnnotations()).contains(url)) {
                    m = md.getName();
                    return m;
                }
            }
        }
        return m;
    }
}