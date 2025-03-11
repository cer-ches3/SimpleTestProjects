package org.example.news.services;

import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface CRUDService <T>{
    T getByID(Long id);
    Collection<T> getAll();
    ResponseEntity create(T item);
    ResponseEntity update(T item);
    ResponseEntity delete(Long id);
}
