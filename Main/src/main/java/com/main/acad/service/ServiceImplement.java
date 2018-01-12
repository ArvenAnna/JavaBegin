package com.main.acad.service;

import com.main.acad.dao.ChapterDao;
import com.main.acad.dao.Dao;
import com.main.acad.entity.Chapter;
import lombok.Getter;
import lombok.Setter;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Getter
@Setter
public class ServiceImplement implements Service {
    private Dao dao = new ChapterDao();

    public ServiceImplement() throws SQLException, ClassNotFoundException {
    }

    @Override
    public String getInformstioAboutChildren(String name) throws IOException {
        FileReader fileReader =  this.dao.getInformstioAboutChildren(name);
        String s = "";
        int c;
        while ((c = fileReader.read()) != -1) {
            s+=((char) c);
        }
        return s;
    }

    public List<Chapter> listChapters() throws SQLException, InterruptedException {
        return this.dao.listChapters();
    }

    @Override
    public List<Chapter> listChildren(int id) {
        return this.dao.listChildren(id);
    }

//    public static void main(String[] args) {
//        ServiceImplement serviceImplement = new ServiceImplement();
//        System.out.println(serviceImplement.listChapters());
//    }
}

