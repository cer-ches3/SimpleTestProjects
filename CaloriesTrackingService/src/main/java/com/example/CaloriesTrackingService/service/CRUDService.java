package com.example.CaloriesTrackingService.service;

import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface CRUDService<T> {
    ResponseEntity getById(long id);

    Collection<T> getAll();

    ResponseEntity create(T item);

    ResponseEntity update(T item);

    ResponseEntity deleteById(long id);
}
