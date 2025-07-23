package dev.sideproject.ndx.mapper;

import dev.sideproject.ndx.dto.request.ReviewRequest;
import dev.sideproject.ndx.dto.response.ReviewResponse;
import dev.sideproject.ndx.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    Review toReview(ReviewRequest reviewRequest);

    ReviewResponse toReviewResponse(Review review);
}
