package main.java.com.main.acad.entity;

import java.util.List;

public class Chapter {
    private int id;
    private String name;
    private List<Chapter> subchapters;
    private String referenceOnFile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Chapter> getSubchapters() {
        return subchapters;
    }

    public void setSubchapters(List<Chapter> subchapters) {
        this.subchapters = subchapters;
    }

    public String getreferenceOnFile() {
        return referenceOnFile;
    }

    public void setreferenceOnFile(String referenceOnFile) {
        this.referenceOnFile = referenceOnFile;
    }

    public Chapter() {
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subchapter=" + subchapters +
                ", referenceOnFile='" + referenceOnFile + '\'' +
                '}';
    }
}
