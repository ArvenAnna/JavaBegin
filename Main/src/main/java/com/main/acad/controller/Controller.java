package com.main.acad.controller;

import com.main.acad.annotation.MappingMethod;
import com.main.acad.dao.ChapterDao;
import com.main.acad.dao.Dao;
import com.main.acad.entity.Chapter;
import com.main.acad.serializator.MyJsonSerializer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Controller {
    @MappingMethod(url = "api/chapter")
    public HttpServletResponse returnChapters(HttpServletRequest request, HttpServletResponse response)
            throws ClassNotFoundException, IllegalAccessException, IOException {
        Dao dao = new ChapterDao();
        List chaptersList = dao.listChapters();
        MyJsonSerializer m = new MyJsonSerializer();
        String result = m.write(chaptersList);
        response.getWriter().write(result);
        return response;
    }

    @MappingMethod(url = "api/subChapter")
    public HttpServletResponse returnSubChapters(HttpServletRequest request, HttpServletResponse response)
            throws ClassNotFoundException, IllegalAccessException, IOException {
        Dao d = new ChapterDao();
        List<String> child = d.listChildren(Integer.parseInt("3"));
        MyJsonSerializer m2 = new MyJsonSerializer();
        response.getWriter().write(m2.write(child));
        return response;
    }
}
