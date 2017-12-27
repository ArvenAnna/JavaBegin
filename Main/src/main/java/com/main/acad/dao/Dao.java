package com.main.acad.dao;

import com.main.acad.entity.Chapter;

import java.util.List;

public interface Dao {

    void addChapter(Chapter chapter) throws ClassNotFoundException;

    void updateChapter(Chapter chapter) throws ClassNotFoundException;

    void removeChapter(int id) throws ClassNotFoundException;

    Chapter getChapter(int id) throws ClassNotFoundException;

    List<Chapter> listChapters() throws ClassNotFoundException;

    List listChildren(int id) throws ClassNotFoundException;

}
