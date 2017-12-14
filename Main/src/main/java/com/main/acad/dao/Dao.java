package main.java.com.main.acad.dao;

import main.java.com.main.acad.entity.Chapter;
import java.util.List;

public interface Dao {

         void addChapter(Chapter chapter);

         void updateChapter(Chapter chapter);

         void removeChapter(int id);

         Chapter getChapter(int id);

         List<Chapter> listChapters();

}
