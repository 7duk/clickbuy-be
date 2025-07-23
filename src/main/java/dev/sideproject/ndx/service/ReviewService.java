package dev.sideproject.ndx.service;


import dev.sideproject.ndx.dto.response.ReviewResponse;
import dev.sideproject.ndx.dto.request.ReviewRequest;

import java.util.List;

public interface ReviewService {
    List<ReviewResponse> findByItemId(Integer itemId);

    ReviewResponse save(ReviewRequest reviewRequest, Integer itemId);
}
