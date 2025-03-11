package org.example.news.controllers;

import lombok.RequiredArgsConstructor;
import org.example.news.dto.CategoryCRUDService;
import org.example.news.dto.CategoryDto;
import org.example.news.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping(path = "/api/categories")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryCRUDService categoryService;
    private final CategoryRepository categoryRepository;
    private String error;

    @GetMapping
    public Collection<CategoryDto> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        if (categoryRepository.existsById(id)) {
            return new ResponseEntity<>(categoryService.getByID(id), HttpStatus.OK);
        } else {
            error = "\"message\": \"Категория с id " + id + " не найдена.\"";
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        categoryDto.setId(id);
        return categoryService.update(categoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (categoryRepository.existsById(id)) {
            return categoryService.delete(id);
        }else {
            error = "\"message\": \"Категория с id " + id + " не найдена.\"";
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

    }
}
