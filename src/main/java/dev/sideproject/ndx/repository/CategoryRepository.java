package dev.sideproject.ndx.repository;

import dev.sideproject.ndx.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByIsDeleted(int isDeleted);
}
