package dev.sideproject.ndx2.controller;

import dev.sideproject.ndx2.dto.ReviewRequest;
import dev.sideproject.ndx2.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("items")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController extends Controller {
    ReviewService reviewService;

    @GetMapping("/{itemId}/reviews")
    public ResponseEntity<?> getReviewsByItemId(@PathVariable("itemId") Integer itemId) {
        return response(HttpStatus.OK, String.format("get reviews from item id: %s successfully", itemId),
                reviewService.findByItemId(itemId));
    }

    @PostMapping("/{itemId}/reviews")
    public ResponseEntity<?> saveReview(@PathVariable("itemId") Integer itemId,
                                        @Valid @RequestBody ReviewRequest reviewRequest) {
        return response(HttpStatus.CREATED, "saved review successfully",
                reviewService.save(reviewRequest, itemId));
    }
}
