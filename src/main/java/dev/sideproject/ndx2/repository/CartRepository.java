package dev.sideproject.ndx2.repository;

import dev.sideproject.ndx2.entity.Cart;
import dev.sideproject.ndx2.entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends RepositoryInterface<Cart, Integer> {
    List<Cart> findByAccountId(Integer accountId);
    Optional<Cart> findByAccountIdAndItem(Integer accountId, Item item);
}
