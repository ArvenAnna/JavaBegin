package com.main.acad.controller;

import com.main.acad.annotation.Mapping;
import com.main.acad.dao.ChapterDao;
import com.main.acad.dao.Dao;
import com.main.acad.entity.Chapter;
import com.main.acad.serializator.MyJsonSerializer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Mapping(url = "api/chapter")
public class ControllerChapter {

    public HttpServletResponse returnResponce(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, IllegalAccessException, IOException {
        Dao dao = new ChapterDao();
        List<Chapter> chaptersList = dao.listChapters();
        MyJsonSerializer m = new MyJsonSerializer();
        String result = m.write(chaptersList);
        response.getWriter().write(result);
        return response;
    }
}
