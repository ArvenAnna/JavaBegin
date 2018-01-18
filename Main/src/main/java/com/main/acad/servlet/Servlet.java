package com.main.acad.servlet;

import com.main.acad.annotation.MappingMethod;
import com.main.acad.error.ControllerNotFoundException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Servlet extends HttpServlet implements javax.servlet.Servlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        String url = request.getRequestURL().toString();
        url = url.substring(url.indexOf("4") + 2);
        try {
            invokeController(url, request, response);
        } catch (Exception e) {
            try {
                logger.info("An error occurred in the HttpServlet class in the doGet method" + e.getMessage());
                response.sendRedirect(response.encodeRedirectURL("/exception_page.html"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void invokeController(String url, HttpServletRequest request, HttpServletResponse response) {
        List<Class<?>> listClasses = null;
        try {
            listClasses = getClasses("com.main.acad.controller");
        } catch (Exception e) {
            logger.info("An error occurred in the HttpServlet class in the invokeController method" + e.getMessage());
        }
        for (Class<?> classForech : listClasses) {
            String getMethod = getMethod(classForech, url);
            if (getMethod != null) {
                try {
                    Class clazz = Class.forName(classForech.getName());
                    Object newInstance = clazz.newInstance();
                    Class newInstanceClass = newInstance.getClass();
                    Class[] paramTypes = new Class[]{HttpServletRequest.class, HttpServletResponse.class};
                    Method method = newInstanceClass.getMethod(getMethod, paramTypes);
                    Object[] args = new Object[]{request, response};
                    method.invoke(newInstance, args);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    logger.info("An error occurred in the HttpServlet class in the invokeController method" + e.getMessage());
                    throw new ControllerNotFoundException("error");
                }
            }
        }
    }

    private List<Class<?>> getClasses(String namePackage) {
        ClassLoader classLoader = MappingMethod.class.getClassLoader();
        String packageDir = namePackage.replaceAll("[.]", "/");
        List<Class<?>> listClasses = new ArrayList<>();
        URL upackage = classLoader.getResource(packageDir);
        InputStream inputStream = null;
        try {
            inputStream = (InputStream) upackage.getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.endsWith(".class"))
                    listClasses.add(Class.forName(namePackage + "." + line.substring(0, line.lastIndexOf('.'))));
            }
            return listClasses;
        } catch (IOException | ClassNotFoundException e) {
            logger.info("An error occurred in the HttpServlet class in the getClasses method" + e.getMessage());
        }
        return listClasses;
    }

    private String getMethod(Class clazz, String url) {
        Method[] method = clazz.getMethods();
        String nameMethod = null;
        for (Method md : method) {
            if (md.isAnnotationPresent(MappingMethod.class)) {
                if (Arrays.toString(md.getAnnotations()).contains(url)) {
                    nameMethod = md.getName();
                    return nameMethod;
                }
            }
        }
        return nameMethod;
    }
}