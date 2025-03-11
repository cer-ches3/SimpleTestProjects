package org.example.news.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.news.entity.Category;
import org.example.news.repositories.CategoryRepository;
import org.example.news.services.CRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryCRUDService implements CRUDService<CategoryDto> {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto getByID(Long id) {
        return mapToDto(categoryRepository.findById(id).orElseThrow());
    }

    @Override
    public Collection<CategoryDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryCRUDService::mapToDto)
                .toList();
    }

    @Override
    public ResponseEntity create(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        categoryRepository.save(category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity update(CategoryDto categoryDto) {
        long id = categoryDto.getId();
        if (categoryRepository.findAll().contains(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Category category = mapToEntity(categoryDto);
        categoryRepository.save(category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @Override
    public ResponseEntity delete(Long id) {
        categoryRepository.deleteById(id);
        return null;
    }

    public static CategoryDto mapToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
        categoryDto.setNewsList(
                category.getNewsList()
                        .stream()
                        .map(NewsCRUDService::mapToDto)
                        .toList()
        );
        return categoryDto;
    }

    public static Category mapToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setTitle(categoryDto.getTitle());
        category.setNewsList(
                categoryDto.getNewsList()
                        .stream()
                        .map(NewsCRUDService::mapToEntity)
                        .toList()
        );
        return category;
    }
}
