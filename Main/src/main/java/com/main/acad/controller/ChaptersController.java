package com.main.acad.controller;

import com.main.acad.annotation.MappingMethod;
import com.main.acad.entity.Chapter;
import com.main.acad.error.ChapterDaoFailedExeption;
import com.main.acad.error.ConnectionPoolFailedException;
import com.main.acad.error.ControllerNotFoundException;
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
            String jsonString = jsonSerializator.write(chaptersList);
            response.getWriter().write(jsonString);
        } catch (IllegalAccessException | IOException | ChapterDaoFailedExeption | ConnectionPoolFailedException e) {
            logger.info("An error occurred in the ChaptersController in the returnChapters method" + e.getMessage());
            throw new ControllerNotFoundException(e.getMessage());
        }
    }

    @MappingMethod(url = "api/subChapter")
    public void returnSubChapters(HttpServletRequest request, HttpServletResponse response) {
        List<Chapter> childrenList = chaptersServiceImplementation.listChildren(Integer.parseInt(request.getParameter("id")));
        JsonSerializatorImplementation jsonSerializer = new JsonSerializatorImplementation();
        try {
            String jsonString = jsonSerializer.write(childrenList);
            response.getWriter().write(jsonString);
        } catch (IllegalAccessException | IOException | ChapterDaoFailedExeption | ConnectionPoolFailedException e) {
            logger.info("An error occurred in the ChaptersController in the returnSubChapters method" + e.getMessage());
            throw new ControllerNotFoundException(e.getMessage());
        }
    }

    @MappingMethod(url = "api/subChapterByName")
    public void returnSubChaptersById(HttpServletRequest request, HttpServletResponse response) {
        try {
            String subChapterDate = chaptersServiceImplementation.getInformstioAboutChildren(request.getParameter("name"));
            response.getWriter().write(subChapterDate);
        } catch (IOException | ChapterDaoFailedExeption | ConnectionPoolFailedException e) {
            logger.info("An error occurred in the ChaptersController in the returnSubChaptersById method" + e.getMessage());
            throw new ControllerNotFoundException(e.getMessage());
        }
    }
}
