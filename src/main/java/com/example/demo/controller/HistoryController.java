package com.example.demo.controller;

import com.example.demo.repository.history.HistoryBookTakenRepository;
import com.example.demo.repository.history.HistoryReservationRepository;
import com.example.demo.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("History")
public class HistoryController {
    @Autowired
    private HistoryBookTakenRepository historyBookTakenRepository;
    @Autowired
    private HistoryReservationRepository historyReservationRepository;

    @RequestMapping(value = "/popular_taken", method = RequestMethod.GET)
    public ResponseEntity<?> takeByMostPopularBookTaken(@RequestParam(value = "limit") int limit) {
        List historyBook = historyBookTakenRepository.getMostPopular();
        return ListService.convertToResponseEntity(historyBook, limit);
    }

    @RequestMapping(value = "/not_popular_taken", method = RequestMethod.GET)
    public ResponseEntity<?> takeByNotPopularBookTaken(@RequestParam(value = "limit") int limit) {
        List historyBook = historyBookTakenRepository.getNotMostPopular();
        return ListService.convertToResponseEntity(historyBook, limit);
    }

    @RequestMapping(value = "/popular_author_taken", method = RequestMethod.POST)
    public ResponseEntity<?> takeByMostPopularBookAuthorTaken(@RequestParam(value = "limit") int limit,
                                                              @RequestParam(value = "author") String author) {
        List historyBook = historyBookTakenRepository.getMostPopularAuthor(author);
        return ListService.convertToResponseEntity(historyBook, limit);
    }

    @RequestMapping(value = "/not_popular_author_taken", method = RequestMethod.POST)
    public ResponseEntity<?> takeByNotPopularBookAuthorTaken(@RequestParam(value = "limit") int limit,
                                                             @RequestParam(value = "author") String author) {
        List historyBook = historyBookTakenRepository.getNotMostPopularAuthor(author);
        return ListService.convertToResponseEntity(historyBook, limit);
    }

    @RequestMapping(value = "/popular_genre_taken", method = RequestMethod.POST)
    public ResponseEntity<?> takeByMostPopularBookGenreTaken(@RequestParam(value = "limit") int limit,
                                                             @RequestParam(value = "genre") String genre) {
        List historyBook = historyBookTakenRepository.getMostPopularGenre(genre);
        return ListService.convertToResponseEntity(historyBook, limit);
    }

    @RequestMapping(value = "/not_popular_genre_taken", method = RequestMethod.POST)
    public ResponseEntity<?> takeByNotPopularBookGenreTaken(@RequestParam(value = "limit") int limit,
                                                            @RequestParam(value = "genre") String genre) {
        List historyBook = historyBookTakenRepository.getNotMostPopularGenre(genre);
        return ListService.convertToResponseEntity(historyBook, limit);
    }

    @RequestMapping(value = "/popular_repository", method = RequestMethod.GET)
    public ResponseEntity<?> takeByMostPopularBookRepository(@RequestParam(value = "limit") int limit) {
        List historyBook = historyReservationRepository.getMostPopular();
        return ListService.convertToResponseEntity(historyBook, limit);
    }

    @RequestMapping(value = "/not_popular_repository", method = RequestMethod.GET)
    public ResponseEntity<?> takeByNotPopularBookRepository(@RequestParam(value = "limit") int limit) {
        List historyBook = historyReservationRepository.getNotMostPopular();
        return ListService.convertToResponseEntity(historyBook, limit);
    }

    @RequestMapping(value = "/popular_author_repository", method = RequestMethod.POST)
    public ResponseEntity<?> takeByMostPopularBookAuthorRepository(@RequestParam(value = "limit") int limit,
                                                                   @RequestParam(value = "author") String author) {
        List historyBook = historyReservationRepository.getMostPopularAuthor(author);
        return ListService.convertToResponseEntity(historyBook, limit);
    }

    @RequestMapping(value = "/not_popular_author_repository", method = RequestMethod.POST)
    public ResponseEntity<?> takeByNotPopularBookAuthorRepository(@RequestParam(value = "limit") int limit,
                                                                  @RequestParam(value = "author") String author) {
        List historyBook = historyReservationRepository.getNotMostPopularAuthor(author);
        return ListService.convertToResponseEntity(historyBook, limit);
    }

    @RequestMapping(value = "/popular_genre_repository", method = RequestMethod.POST)
    public ResponseEntity<?> takeByMostPopularBookGenreRepository(@RequestParam(value = "limit") int limit,
                                                                  @RequestParam(value = "genre") String genre) {
        List historyBook = historyReservationRepository.getMostPopularGenre(genre);
        return ListService.convertToResponseEntity(historyBook, limit);
    }

    @RequestMapping(value = "/not_popular_genre_repository", method = RequestMethod.POST)
    public ResponseEntity<?> takeByNotPopularBookGenreRepository(@RequestParam(value = "limit") int limit,
                                                                 @RequestParam(value = "genre") String genre) {
        List historyBook = historyReservationRepository.getNotMostPopularGenre(genre);
        return ListService.convertToResponseEntity(historyBook, limit);
    }
}
