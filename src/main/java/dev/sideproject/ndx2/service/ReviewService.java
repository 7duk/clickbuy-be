package dev.sideproject.ndx2.service;


import dev.sideproject.ndx2.dto.request.ReviewRequest;
import dev.sideproject.ndx2.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {
    List<ReviewResponse> findByItemId(Integer itemId);
    ReviewResponse save(ReviewRequest reviewRequest, Integer itemId);
}
