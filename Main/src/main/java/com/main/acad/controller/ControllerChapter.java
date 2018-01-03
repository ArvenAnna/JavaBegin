package com.main.acad.controller;

import com.main.acad.annotation.AnnotationForControllers;
import com.main.acad.dao.ChapterDao;
import com.main.acad.dao.Dao;
import com.main.acad.serializator.MyJsonSerializer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@AnnotationForControllers(url = "api/chapter")
public class ControllerChapter {
    public ControllerChapter(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, IllegalAccessException, IOException {
        Dao dao = new ChapterDao();
        List chaptersList = dao.listChapters();
        MyJsonSerializer m = new MyJsonSerializer();
        String result = m.write(chaptersList);
        response.getWriter().write(result);

    }
}
