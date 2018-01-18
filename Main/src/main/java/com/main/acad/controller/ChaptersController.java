package com.main.acad.controller;

import com.main.acad.annotation.MappingMethod;
import com.main.acad.entity.Chapter;
import com.main.acad.error.ChapterDaoFailedExeption;
import com.main.acad.serializator.JsonSerializatorImplementation;
import com.main.acad.service.ChaptersServiceImplementation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class ChaptersController {

    private static final Logger logger = Logger.getLogger(ChaptersController.class.getName());
    private ChaptersServiceImplementation chaptersServiceImplementation = ChaptersServiceImplementation.getInstance();

    @MappingMethod(url = "api/chapter")
    public void returnChapters(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Chapter> chaptersList = chaptersServiceImplementation.listChapters();
            JsonSerializatorImplementation jsonSerializator = new JsonSerializatorImplementation();
            String result = jsonSerializator.write(chaptersList);
            response.getWriter().write(result);
        } catch (IllegalAccessException | ChapterDaoFailedExeption | IOException e) {
            logger.info("An error occurred in the Controller in the returnChapters method" + e.getMessage());
            throw new IllegalArgumentException();
        }
    }

    @MappingMethod(url = "api/subChapter")
    public void returnSubChapters(HttpServletRequest request, HttpServletResponse response) {
        List<Chapter> child = chaptersServiceImplementation.listChildren(Integer.parseInt(request.getParameter("id")));
        JsonSerializatorImplementation jsonSerializer = new JsonSerializatorImplementation();
        try {
            String result = jsonSerializer.write(child);
            response.getWriter().write(result);
        } catch (IllegalAccessException | IOException e) {
            logger.info("An error occurred in the Controller in the returnSubChapters method" + e.getMessage());
            throw new IllegalArgumentException();
        }
    }

    @MappingMethod(url = "api/subChapterByName")
    public void returnSubChaptersById(HttpServletRequest request, HttpServletResponse response) {
        try {
            String chapter = chaptersServiceImplementation.getInformstioAboutChildren(request.getParameter("name"));
            response.getWriter().write(chapter);
        } catch (IOException e) {
            logger.info("An error occurred in the Controller in the returnSubChaptersById method" + e.getMessage());
            throw new IllegalArgumentException();
        }
    }
}
