package dev.sideproject.ndx.repository;

import dev.sideproject.ndx.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends RepositoryInterface<Item,Integer> {
    List<Item> findByNameContaining(String keyword);
}
