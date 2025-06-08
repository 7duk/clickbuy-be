package dev.sideproject.ndx2.repository;

import dev.sideproject.ndx2.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends RepositoryInterface<Item,Integer> {
}
