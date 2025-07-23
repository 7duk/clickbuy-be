package dev.sideproject.ndx.repository;

import dev.sideproject.ndx.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends MongoRepository<Review, UUID> {
    List<Review> findByItemId(Integer itemId);
}
