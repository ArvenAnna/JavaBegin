package com.main.acad.service;

import com.main.acad.entity.Chapter;

import java.util.List;

public interface ChaptersService {

    String getInformstioAboutChildren(String name);

    List<Chapter> listChapters();

    List<Chapter> listChildren(int id);
}
