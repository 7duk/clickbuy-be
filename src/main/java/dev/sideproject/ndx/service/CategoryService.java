package dev.sideproject.ndx.service;

import dev.sideproject.ndx.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findCategoriesActive(int isDeleted);
}
