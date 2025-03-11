package org.example.news.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.news.entity.Category;
import org.example.news.entity.News;
import org.example.news.repositories.CategoryRepository;
import org.example.news.repositories.NewsRepository;
import org.example.news.services.CRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsCRUDService implements CRUDService<NewsDto> {
    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public NewsDto getByID(Long id) {
        News news = newsRepository.findById(id).orElseThrow();
        return mapToDto(news);
    }

    @Override
    public Collection<NewsDto> getAll() {
        return newsRepository.findAll()
                .stream()
                .map(NewsCRUDService::mapToDto)
                .toList();
    }

    public Collection<NewsDto> findByCategoryID(Long id) {
        return newsRepository.findAll()
                .stream()
                .filter(n -> n.getCategory().getId().equals(id))
                .map(NewsCRUDService::mapToDto)
                .toList();
    }

    @Override
    public ResponseEntity create(NewsDto newsDto) {
        News news = mapToEntity(newsDto);
        Long categoryId = newsDto.getCategoryId();
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        news.setCategory(category);
        newsRepository.save(news);
        return new ResponseEntity<>(news, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity update(NewsDto newsDto) {
        News news = mapToEntity(newsDto);
        Long categoryId = newsDto.getCategoryId();
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        news.setCategory(category);
        newsRepository.save(news);
        return new ResponseEntity<>(news, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity delete(Long id) {
        newsRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public static NewsDto mapToDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setText(news.getText());
        newsDto.setDateTime(news.getDateTime());
        newsDto.setCategoryId(news.getCategory().getId());
        return newsDto;
    }

    public static News mapToEntity(NewsDto newsDto) {
        News news = new News();
        news.setId(newsDto.getId());
        news.setTitle(newsDto.getTitle());
        news.setText(newsDto.getText());
        news.setDateTime(newsDto.getDateTime());
        return news;
    }
}
