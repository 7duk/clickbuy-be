package dev.sideproject.ndx2.controller;

import dev.sideproject.ndx2.dto.response.CategoryResponse;
import dev.sideproject.ndx2.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController extends Controller{
    CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> getCategories() {
        List<CategoryResponse> categories = categoryService.findCategoriesActive(0);
        return response(HttpStatus.OK,"get all categories without items",categories);
    }
}
