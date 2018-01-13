package com.main.acad.servlet;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.FilterConfig;
import java.io.IOException;


public class HttpServletFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //Setting the character set for the request
        request.setCharacterEncoding("UTF-8");

        // pass the request on
        chain.doFilter(request, response);

        //Setting the character set for the response
        response.setContentType("text/html; charset=UTF-8");
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) {

    }
}