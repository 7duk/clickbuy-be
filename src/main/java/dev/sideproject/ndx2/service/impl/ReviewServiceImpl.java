package dev.sideproject.ndx2.service.impl;

import dev.sideproject.ndx2.dto.request.ReviewRequest;
import dev.sideproject.ndx2.dto.response.ReviewResponse;
import dev.sideproject.ndx2.entity.Review;
import dev.sideproject.ndx2.exception.AppException;
import dev.sideproject.ndx2.exception.ErrorCode;
import dev.sideproject.ndx2.mapper.ReviewMapper;
import dev.sideproject.ndx2.repository.AccountRepository;
import dev.sideproject.ndx2.repository.ReviewRepository;
import dev.sideproject.ndx2.service.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewServiceImpl implements ReviewService {
    ReviewRepository reviewRepository;
    AccountRepository accountRepository;

    @Override
    public List<ReviewResponse> findByItemId(Integer itemId) {
        return reviewRepository.findByItemId(itemId).stream()
                .map(review -> {
                    ReviewResponse reviewResponse = ReviewMapper.INSTANCE.toReviewResponse(review);
                    reviewResponse.setFullname(accountRepository.findFullNameById(review.getLastModifiedBy()));
                    return reviewResponse;
                })
                .toList();
    }

    @Override
    public ReviewResponse save(ReviewRequest reviewRequest, Integer itemId) {
        try {
            Review reviewMapped = ReviewMapper.INSTANCE.toReview(reviewRequest);
            reviewMapped.setId(UUID.randomUUID());
            reviewMapped.setItemId(itemId);
            reviewMapped.setLastModifiedAt(LocalDateTime.now());
            ReviewResponse reviewResponse = ReviewMapper.INSTANCE.toReviewResponse(reviewRepository.save(reviewMapped));
            reviewResponse.setFullname(accountRepository.findFullNameById(reviewRequest.getLastModifiedBy()));
            return reviewResponse;
        } catch (Exception e) {
            log.error("Error while saving review because {}", e.getMessage());
            throw new AppException(ErrorCode.SAVE_ERROR);
        }
    }
}
