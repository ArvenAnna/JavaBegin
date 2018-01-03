package com.main.acad.controller;

import com.main.acad.annotation.AnnotationForControllers;
import com.main.acad.dao.ChapterDao;
import com.main.acad.dao.Dao;
import com.main.acad.serializator.MyJsonSerializer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@AnnotationForControllers(url = "api/subChapter")
public class ControllerSubChapter {
    public ControllerSubChapter(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, IllegalAccessException, IOException {
        Dao d = new ChapterDao();
        List child = null;
        child = d.listChildren(Integer.parseInt("request"));
        MyJsonSerializer m2 = new MyJsonSerializer();
        String res = "";
        res = m2.write(child);
        response.getWriter().write(res);
    }
}
