package dev.sideproject.ndx2.service;


import dev.sideproject.ndx2.dto.ReviewRequest;
import dev.sideproject.ndx2.dto.ReviewResponse;
import dev.sideproject.ndx2.entity.Review;

import java.util.List;

public interface ReviewService {
    List<ReviewResponse> findByItemId(Integer itemId);
    ReviewResponse save(ReviewRequest reviewRequest, Integer itemId);
}
