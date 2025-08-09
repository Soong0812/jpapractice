package com.jpapractice.service;

import com.jpapractice.dto.MovieRequest;
import com.jpapractice.dto.MovieResponse;
import com.jpapractice.entity.Movie;
import com.jpapractice.repository.MovieRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    @Transactional
    public MovieResponse save(MovieRequest request) {
        Movie movie = new Movie(request.getTitle());
        Movie savedMovie = movieRepository.save(movie);
        return new MovieResponse(
                savedMovie.getId(),
                savedMovie.getTitle()
        );
    }

    @Transactional
    public List<MovieResponse> findAll() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movie -> new MovieResponse(
                        movie.getId(),
                        movie.getTitle()
                )).toList();
    }

    //단건조회 (readOnly)
    @Transactional(readOnly = true)
    public MovieResponse findById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new IllegalArgumentException("그런 movieId의 movie는 없습니다"));
        return new MovieResponse(movie.getId(), movie.getTitle());
    }

    //수정
    @Transactional
    public MovieResponse update(Long movieId, MovieRequest request) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new IllegalArgumentException("그런 movieId의 movie는 없습니다"));
        movie.updateTitle(request.getTitle());
        return new MovieResponse(movie.getId(), movie.getTitle());
    }

}
