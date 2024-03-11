package com.java.lab05.DAO;

import java.util.List;

public interface iDAO <T>{
    List<T> getAll();
    T getOneById(Long id);
    boolean isExistById(Long id);
    boolean removeById(Long id);
    boolean add(T object);
    boolean merge(T object);

}
