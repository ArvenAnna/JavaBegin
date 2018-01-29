package com.main.acad.servlet;

import com.main.acad.dao.SimpleUserDao;
import com.main.acad.dao.UserDao;
import com.main.acad.error.NoAccessToFileException;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class CookieFilter implements Filter {
    private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURL().toString();
        Cookie arrayCookies[] = httpServletRequest.getCookies();
        String cookieValue = "";
        for (Cookie cokies : arrayCookies) {
            cookieValue = cokies.getValue();
        }
        UserDao dao = SimpleUserDao.getInstance();
        String userStatus = dao.findUserByLogin(cookieValue);
        if (userStatus.equals("admin")) {
            try {
                chain.doFilter(request, response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        } else {
            if (url.equals("http://localhost:8084/api/createFile") ||
                    url.equals("http://localhost:8084/api/deleteSubChapter") ||
                    url.equals("http://localhost:8084/api/updateSubChapter")
                    ) {
            } else {
                try {
                    chain.doFilter(request, response);
                } catch (ServletException | IOException e) {
                    throw new NoAccessToFileException("error in Filter");
                }
            }
        }
    }

    @Override
    public void destroy() {

    }

}
