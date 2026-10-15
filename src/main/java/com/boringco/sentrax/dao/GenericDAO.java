package com.boringco.sentrax.dao;
import java.util.*;
public interface GenericDAO<T, ID> {
    void save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
}
