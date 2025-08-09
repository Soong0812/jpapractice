package com.jpapractice.repository;

import com.jpapractice.entity.Movie;
import com.jpapractice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByMovie(Movie movie);
}
