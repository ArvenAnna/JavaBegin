package com.main.acad.service;

import com.main.acad.entity.Chapter;

import java.util.List;

public interface StrategySimulationService {

    String getInformstioAboutChildren(String name);

    List<Chapter> getlistChapters();

    List<Chapter> getlistChildren(int id);
}
