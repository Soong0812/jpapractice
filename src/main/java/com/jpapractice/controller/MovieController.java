package com.jpapractice.controller;


import com.jpapractice.dto.MovieRequest;
import com.jpapractice.dto.MovieResponse;
import com.jpapractice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping("/movies")
    public ResponseEntity<MovieResponse> saveMovie(
            @RequestBody MovieRequest request
    ) {
        return ResponseEntity.ok(movieService.save(request));
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieResponse>> getAllMovie() {
        return ResponseEntity.ok(movieService.findAll());
    }

    //조회
    @GetMapping("/movies/{movieId}")
    public ResponseEntity<MovieResponse> getMovieById(
            @PathVariable Long movieId
    ) {
        return ResponseEntity.ok(movieService.findById(movieId));
    }

    //수정(Update)
    @PutMapping("/movies/{movieId}")
    public ResponseEntity<MovieResponse> updateMovie(
            @PathVariable Long movieId,
            @RequestBody MovieRequest request
    ) {
        return ResponseEntity.ok(movieService.update(movieId, request));
    }
}
