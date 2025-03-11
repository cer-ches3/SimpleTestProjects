package org.example.news.controllers;

import jdk.jfr.Timestamp;
import lombok.RequiredArgsConstructor;
import org.example.news.dto.NewsCRUDService;
import org.example.news.dto.NewsDto;
import org.example.news.repositories.NewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping(path = "/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsCRUDService newsService;
    private final NewsRepository newsRepository;
    private String error;

    @GetMapping("/{id}")
    public ResponseEntity getNewsByID(@PathVariable Long id) {
        if (newsRepository.existsById(id)) {
            return new ResponseEntity(newsService.getByID(id), HttpStatus.OK);
        }else {
            error = "\"message\": \"Новость с id " + id + " не найдена.\"";
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping
    public Collection<NewsDto> getAllNews() {
        return newsService.getAll();
    }

    @GetMapping(path = "/categories/{id}")
    public Collection<NewsDto> findByCategoryID(@PathVariable Long id) {
        return newsService.findByCategoryID(id);
    }

    @PostMapping
    public ResponseEntity createNews (@RequestBody NewsDto newsDto) {
        return newsService.create(newsDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateNews(@PathVariable Long id, @RequestBody NewsDto newsDto) {
        newsDto.setId(id);
        newsDto.setDateTime(LocalDateTime.now());
        return newsService.update(newsDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNews(@PathVariable Long id) {
        return newsService.delete(id);
    }
}
