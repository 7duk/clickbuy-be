package dev.sideproject.ndx.repository;

import dev.sideproject.ndx.entity.Cart;
import dev.sideproject.ndx.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends RepositoryInterface<Cart, Integer> {
    List<Cart> findByAccountId(Integer accountId);

    Optional<Cart> findByAccountIdAndItem(Integer accountId, Item item);
}
