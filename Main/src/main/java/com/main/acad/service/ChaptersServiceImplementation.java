package com.main.acad.service;

import com.main.acad.dao.ChapterDao;
import com.main.acad.dao.SimpleChapterDao;
import com.main.acad.entity.Chapter;
import com.main.acad.error.ChapterDaoFailedExeption;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class ChaptersServiceImplementation implements ChaptersService {

    private ChapterDao dao = SimpleChapterDao.getInstance();
    private static ChaptersServiceImplementation instance;
    private static final Logger logger = Logger.getLogger(ChaptersServiceImplementation.class.getName());

    private ChaptersServiceImplementation() {
    }

    public static ChaptersServiceImplementation getInstance() {
        if (instance == null) {
            instance = new ChaptersServiceImplementation();
        }
        return instance;
    }

    @Override
    public String getInformstioAboutChildren(String name) throws ChapterDaoFailedExeption{
        FileReader fileReader = dao.getInformstioAboutChildren(name);
        StringBuilder filePage = new StringBuilder();
        int count;
        try {
            while ((count = fileReader.read()) != -1) {
                filePage.append((char) count);
            }
            logger.info("File : " + name + " successfully read");
        } catch (IOException e) {
            logger.info("An error occurred in the ChaptersServiceImplementation class in the getInformstioAboutChildren method");
        }
        return filePage.toString();
    }

    @Override
    public List<Chapter> listChapters()throws ChapterDaoFailedExeption {
        return dao.getlistChapters();
    }

    @Override
    public List<Chapter> listChildren(int id)throws ChapterDaoFailedExeption {
        return dao.getlistChildren(id);
    }

}

