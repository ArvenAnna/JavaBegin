package com.main.acad.service;

import com.main.acad.entity.Chapter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Service {
    String getInformstioAboutChildren(String name) throws IOException;

    List<Chapter> listChapters() throws SQLException, InterruptedException;

    List<Chapter> listChildren(int id);
}
