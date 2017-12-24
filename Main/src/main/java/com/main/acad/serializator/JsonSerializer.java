package com.main.acad.serializator;

import java.util.List;

public interface JsonSerializer {
    String write(Object obj) throws IllegalAccessException;
    Object read(String string, Class clazz) throws IllegalAccessException, InstantiationException, ClassNotFoundException;
}
