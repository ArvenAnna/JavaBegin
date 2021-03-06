package com.main.acad.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "name", "subchapters", "referenceOnFile"})
@ToString

public class Chapter {
    private int id;
    private String name;
    private List<Chapter> subchapters;
    private String referenceOnFile;
}