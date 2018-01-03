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

@Mapping(url = "api/subChapter")
public class ControllerSubChapter {

    public String returnResponce(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, IllegalAccessException, IOException {
        Dao d = new ChapterDao();
        List<Chapter> child = d.listChildren(Integer.parseInt("3"));
        MyJsonSerializer m2 = new MyJsonSerializer();
        String res = "";
        res = m2.write(child);
        String result = res;
        return result;
    }
}
