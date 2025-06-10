package dev.sideproject.ndx2.mapper;

import dev.sideproject.ndx2.dto.ReviewRequest;
import dev.sideproject.ndx2.dto.ReviewResponse;
import dev.sideproject.ndx2.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);
    Review toReview(ReviewRequest reviewRequest);
    ReviewResponse toReviewResponse(Review review);
}
