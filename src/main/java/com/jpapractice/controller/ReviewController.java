package com.jpapractice.controller;


import com.jpapractice.dto.ReviewRequest;
import com.jpapractice.dto.ReviewResponse;
import com.jpapractice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/movies/{movieId}/reviews")
    public ResponseEntity<ReviewResponse> saveReview(
            @RequestBody ReviewRequest request,
            @PathVariable Long movieId
    ) {
        return ResponseEntity.ok(reviewService.save(request, movieId));
    }

    @GetMapping("/movies/{movieId}/reviews")
    public ResponseEntity<List<ReviewResponse>> getAllReview(
            @PathVariable Long movieId
    ) {
        return ResponseEntity.ok(reviewService.findAll(movieId));
    }

    @GetMapping("movies/{movieId}/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> getReviewById(
            @PathVariable Long movieId,
            @PathVariable Long reviewId
    ) {
        return ResponseEntity.ok(reviewService.findById(movieId, reviewId));
    }

    @PutMapping("movies/{movieId}/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable Long movieId,
            @PathVariable Long reviewId,
            @RequestBody ReviewRequest request
    ) {
        return ResponseEntity.ok(reviewService.update(movieId, reviewId, request));
    }
}
