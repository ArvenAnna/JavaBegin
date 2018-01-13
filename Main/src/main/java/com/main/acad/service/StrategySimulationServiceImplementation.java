package com.main.acad.service;

import com.main.acad.dao.ChapterDao;
import com.main.acad.entity.Chapter;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class StrategySimulationServiceImplementation implements StrategySimulationService {
    private ChapterDao dao = ChapterDao.getInstance();
    private static StrategySimulationServiceImplementation instance;
    private static final Logger logger = Logger.getLogger(StrategySimulationServiceImplementation.class.getName());

    private StrategySimulationServiceImplementation() {
    }

    public static StrategySimulationServiceImplementation getInstance() {
        if (instance == null) {
            instance = new StrategySimulationServiceImplementation();
        }
        return instance;
    }

    @Override
    public String getInformstioAboutChildren(String name) {
        FileReader fileReader = this.dao.getInformstioAboutChildren(name);
        StringBuilder filePage = new StringBuilder();
        int count;
        try {
            while ((count = fileReader.read()) != -1) {
                filePage.append((char) count);
            }
            logger.info("File : " + name + " successfully read");
        } catch (IOException e) {
            logger.info("An error occurred in the StrategySimulationServiceImplementation class in the getInformstioAboutChildren method");
        }
        return filePage.toString();
    }

    @Override
    public List<Chapter> getlistChapters() {
        return this.dao.getlistChapters();
    }

    @Override
    public List<Chapter> getlistChildren(int id) {
        return this.dao.getlistChildren(id);
    }

}

