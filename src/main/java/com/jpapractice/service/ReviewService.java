package com.jpapractice.service;

import com.jpapractice.dto.ReviewRequest;
import com.jpapractice.dto.ReviewResponse;
import com.jpapractice.entity.Movie;
import com.jpapractice.entity.Review;
import com.jpapractice.repository.MovieRepository;
import com.jpapractice.repository.ReviewRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    @Transactional
    public ReviewResponse save(ReviewRequest request, Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new IllegalArgumentException("그런 movieId의 movie는 없어요")
        );
        Review review = new Review(
                request.getContent(),
                movie
        );
        Review savedReview = reviewRepository.save(review);
        return new ReviewResponse(
                savedReview.getId(),
                savedReview.getContent()
        );
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> findAll(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new IllegalArgumentException("그런 movieId의 movie는 없어요")
        );

        List<Review> movies = reviewRepository.findAllByMovie(movie);
        List<ReviewResponse> dtos = new ArrayList<>();

        for (Review review : movies) {
            dtos.add(
                    new ReviewResponse(
                            review.getId(),
                            review.getContent()
                    )
            );
        }
        return dtos;
    }

    //단건조회 (readOnly)
    @Transactional(readOnly = true)
    public ReviewResponse findById(Long movieId, Long reviewId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new IllegalArgumentException("그런 movieId의 movie는 없습니다")
        );

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new IllegalArgumentException("그런 reviewId의 review는 없습니다")
        );

        if (!review.getMovie().equals(movie)) {
            throw new IllegalArgumentException("해당 영화의 리뷰가 아닙니다.");
        }

        return new ReviewResponse(review.getId(), review.getContent());
    }

    //수정(Update)
    @Transactional
    public ReviewResponse update(Long movieId, Long reviewId, ReviewRequest request) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new IllegalArgumentException("그런 movieId의 movie는 없습니다")
        );

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new IllegalArgumentException("그런 reviewId의 review는 없습니다")
        );

        if (!review.getMovie().getId().equals(movie.getId())) {
            throw new IllegalArgumentException("해당 영화의 리뷰가 아닙니다.");
        }

        review.updateContent(request.getContent());
        return new ReviewResponse(review.getId(), review.getContent());
    }
}
