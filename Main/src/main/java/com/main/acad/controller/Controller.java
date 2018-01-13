package com.main.acad.controller;

import com.main.acad.annotation.MappingMethod;
import com.main.acad.entity.Chapter;
import com.main.acad.serializator.JsonSerializatorImplementation;
import com.main.acad.service.StrategySimulationServiceImplementation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class Controller {

    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    private static Controller instance;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    StrategySimulationServiceImplementation strategySimulationServiceImplementation = StrategySimulationServiceImplementation.getInstance();

    @MappingMethod(url = "api/chapter")
    public void returnChapters(HttpServletRequest request, HttpServletResponse response) {
        try {
            List chaptersList = strategySimulationServiceImplementation.getlistChapters();
            JsonSerializatorImplementation jsonSerializator = new JsonSerializatorImplementation();
            String result = jsonSerializator.write(chaptersList);
            response.getWriter().write(result);
        } catch (IllegalAccessException | IOException e) {
            logger.info("An error occurred in the Controller in the returnChapters method");
            e.printStackTrace();
        }
    }

    @MappingMethod(url = "api/subChapter")
    public void returnSubChapters(HttpServletRequest request, HttpServletResponse response) {
        List<Chapter> child = strategySimulationServiceImplementation.getlistChildren(Integer.parseInt(request.getParameter("id")));
        JsonSerializatorImplementation jsonSerializer = new JsonSerializatorImplementation();
        try {
            String result = jsonSerializer.write(child);
            response.getWriter().write(result);
        } catch (IllegalAccessException | IOException e) {
            logger.info("An error occurred in the Controller in the returnSubChapters method");
            e.printStackTrace();
        }
    }

    @MappingMethod(url = "api/subChapterByName")
    public void returnSubChaptersById(HttpServletRequest request, HttpServletResponse response) {
        try {
            String chapter = strategySimulationServiceImplementation.getInformstioAboutChildren(request.getParameter("name"));
            response.getWriter().write(chapter);
        } catch (IOException e) {
            logger.info("An error occurred in the Controller in the returnSubChaptersById method");
            e.printStackTrace();
        }
    }
}
