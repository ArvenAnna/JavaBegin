package com.main.acad;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "name", "subchapters", "referenceOnFile"})
@ToString
public class Cat {
    private String name;
    private int age;
    private boolean isHungry;
    private Dog enemy;
}
