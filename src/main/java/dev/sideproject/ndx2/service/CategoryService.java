package dev.sideproject.ndx2.service;

import dev.sideproject.ndx2.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findCategoriesActive(int isDeleted);
}
