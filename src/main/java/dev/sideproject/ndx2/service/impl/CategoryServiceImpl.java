package dev.sideproject.ndx2.service.impl;

import dev.sideproject.ndx2.dto.response.CategoryResponse;
import dev.sideproject.ndx2.entity.Category;
import dev.sideproject.ndx2.repository.CategoryRepository;
import dev.sideproject.ndx2.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> findCategoriesActive(int isDeleted) {
        List<Category> categories = categoryRepository.findByIsDeleted(isDeleted);
        if (categories.isEmpty()) {
            return List.of();
        } else {
            return categories.stream().map(category ->
                    CategoryResponse.builder()
                            .id(category.getId())
                            .name(category.getName())
                            .build()
            ).toList();
        }
    }
}
