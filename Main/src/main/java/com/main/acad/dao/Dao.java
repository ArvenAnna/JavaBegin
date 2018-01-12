package com.main.acad.dao;

import com.main.acad.entity.Chapter;

import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface Dao {

    void addChapter(Chapter chapter);

    void updateChapter(Chapter chapter);

    void removeChapter(int id);

    FileReader getInformstioAboutChildren(String name);

    List<Chapter> listChapters() throws SQLException, InterruptedException;

    List<Chapter> listChildren(int id);

}
