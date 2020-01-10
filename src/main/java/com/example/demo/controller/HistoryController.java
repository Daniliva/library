package com.example.demo.controller;

import com.example.demo.repository.history.HistoryBookTakenRepository;
import com.example.demo.repository.history.HistoryReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("History")
public class HistoryController {
    @Autowired
    private HistoryBookTakenRepository historyBookTakenRepository;
    @Autowired
    private HistoryReservationRepository historyReservationRepository;

    @RequestMapping(value = "/popular_taken", method = RequestMethod.GET)
    public List<Object[]> takeByMostPopularBookTaken() {
        return historyBookTakenRepository.getMostPopular();
    }

    @RequestMapping(value = "/not_popular_taken", method = RequestMethod.GET)
    public List<Object[]> takeByNotPopularBookTaken() {
        return historyBookTakenRepository.getNotMostPopular();
    }

    @RequestMapping(value = "/popular_author_taken", method = RequestMethod.POST)
    public List<Object[]> takeByMostPopularBookAuthorTaken(@RequestParam(value = "author") String author) {
        return historyBookTakenRepository.getMostPopularAuthor(author);
    }

    @RequestMapping(value = "/not_popular_author_taken", method = RequestMethod.POST)
    public List<Object[]> takeByNotPopularBookAuthorTaken(@RequestParam(value = "author") String author) {
        return historyBookTakenRepository.getNotMostPopularAuthor(author);
    }

    @RequestMapping(value = "/popular_genre_taken", method = RequestMethod.POST)
    public List<Object[]> takeByMostPopularBookGenreTaken(@RequestParam(value = "genre")String genre) {
        return historyBookTakenRepository.getMostPopularGenre(genre);
    }

    @RequestMapping(value = "/not_popular_genre_taken", method = RequestMethod.POST)
    public List<Object[]> takeByNotPopularBookGenreTaken(@RequestParam(value = "genre") String genre) {
        return historyBookTakenRepository.getNotMostPopularGenre(genre);
    }

    @RequestMapping(value = "/popular_repository", method = RequestMethod.GET)
    public List<Object[]> takeByMostPopularBookRepository() {
        return historyReservationRepository.getMostPopular();
    }

    @RequestMapping(value = "/not_popular_repository", method = RequestMethod.GET)
    public List<Object[]> takeByNotPopularBookRepository() {
        return historyReservationRepository.getNotMostPopular();
    }

    @RequestMapping(value = "/popular_author_repository", method = RequestMethod.POST)
    public List<Object[]> takeByMostPopularBookAuthorRepository(@RequestParam(value = "author") String author) {
        return historyReservationRepository.getMostPopularAuthor(author);
    }

    @RequestMapping(value = "/not_popular_author_repository", method = RequestMethod.POST)
    public List<Object[]> takeByNotPopularBookAuthorRepository(@RequestParam(value = "author")String author) {
        return historyReservationRepository.getNotMostPopularAuthor(author);
    }

    @RequestMapping(value = "/popular_genre_repository", method = RequestMethod.POST)
    public List<Object[]> takeByMostPopularBookGenreRepository(@RequestParam(value = "genre")String genre) {
        return historyReservationRepository.getMostPopularGenre(genre);
    }

    @RequestMapping(value = "/not_popular_genre_repository", method = RequestMethod.POST)
    public List<Object[]> takeByNotPopularBookGenreRepository(@RequestParam(value = "genre") String genre) {
        return historyReservationRepository.getNotMostPopularGenre(genre);
    }
}
