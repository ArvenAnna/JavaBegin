package com.main.acad.controller;

import com.main.acad.annotation.MappingMethod;
import com.main.acad.dao.ChapterDao;
import com.main.acad.dao.Dao;
import com.main.acad.entity.Chapter;
import com.main.acad.serializator.MyJsonSerializer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Controller {
    @MappingMethod(url = "api/chapter")
    public void returnChapters(HttpServletRequest request, HttpServletResponse response)
            throws ClassNotFoundException, IllegalAccessException, IOException {
        Dao dao = new ChapterDao();
        List chaptersList = dao.listChapters();
        MyJsonSerializer m = new MyJsonSerializer();
        String result = m.write(chaptersList);
        response.getWriter().write(result);

    }

    @MappingMethod(url = "api/subChapter")
    public static void returnSubChapters(HttpServletRequest request, HttpServletResponse response)
            throws ClassNotFoundException, IllegalAccessException, IOException {
        Dao d = new ChapterDao();
        List<Chapter> child = d.listChildren(Integer.parseInt(request.getParameter("id")));
        MyJsonSerializer m2 = new MyJsonSerializer();
        String result = m2.write(child);
        response.getWriter().write(result);
    }

    @MappingMethod(url = "api/subChapterByName")
    public static void returnSubChaptersById(HttpServletRequest request, HttpServletResponse response)
            throws ClassNotFoundException, IllegalAccessException, IOException {
        Dao d = new ChapterDao();
        //Chapter chapter = d.getInformstioAboutChildren(request.getParameter("name"));
        String chapter = d.getInformstioAboutChildren(request.getParameter("name"));
        MyJsonSerializer m2 = new MyJsonSerializer();
        String result = m2.write(chapter);
        response.getWriter().write(chapter);
    }
}
