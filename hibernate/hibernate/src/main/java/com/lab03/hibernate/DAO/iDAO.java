package com.lab03.hibernate.DAO;

import java.util.List;

public interface iDAO<T> {
    boolean addOrUpdate(T object);
    T get(String id);
    List<T> getAll();
    boolean remove(String id);
    boolean remove(T object);

}
