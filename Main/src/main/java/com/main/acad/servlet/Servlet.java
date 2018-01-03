package com.main.acad.servlet;

import com.main.acad.annotation.AnnotationForControllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String url = request.getRequestURL().toString();
        try {
            invokeController(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Object invokeController(String url) throws Exception {
        List<Class<?>> classes = getClasses("com.main.acad.controller");
        String urlFull = "[@com.main.acad.annotation.AnnotationForControllers(url=" + url + ")]";
        for (Class<?> e : classes) {
            if (Arrays.toString(e.getAnnotations()).equals(urlFull)) {
                String s = "com.main.acad.controller." + e.getSimpleName();
                Class clazz = Class.forName(s);
                Object objec = clazz.newInstance();
                return objec;
            }
        }
        return null;
    }

    public static List<Class<?>> getClasses(String pack) throws Exception {
        ClassLoader cl = AnnotationForControllers.class.getClassLoader();
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