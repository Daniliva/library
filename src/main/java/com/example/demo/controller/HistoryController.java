package com.example.demo.controller;

import com.example.demo.repository.history.HistoryBookTakenRepository;
import com.example.demo.repository.history.HistoryReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("History")
public class HistoryController {
    @Autowired
    private HistoryBookTakenRepository historyBookTakenRepository;
    @Autowired
    private HistoryReservationRepository historyReservationRepository;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/popular_taken", method = RequestMethod.GET)
    public List<Object[]> takeByMostPopularBookTaken() {
        return historyBookTakenRepository.getMostPopular();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/not_popular_taken", method = RequestMethod.GET)
    public List<Object[]> takeByNotPopularBookTaken() {
        return historyBookTakenRepository.getNotMostPopular();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/popular_author_taken", method = RequestMethod.POST)
    public List<Object[]> takeByMostPopularBookAuthorTaken(@RequestBody String author) {
        return historyBookTakenRepository.getMostPopularAuthor(author);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/not_popular_author_taken", method = RequestMethod.POST)
    public List<Object[]> takeByNotPopularBookAuthorTaken(@RequestBody String author) {
        return historyBookTakenRepository.getNotMostPopularAuthor(author);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/popular_genre_taken", method = RequestMethod.POST)
    public List<Object[]> takeByMostPopularBookGenreTaken(@RequestBody String genre) {
        return historyBookTakenRepository.getMostPopularGenre(genre);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/not_popular_genre_taken", method = RequestMethod.POST)
    public List<Object[]> takeByNotPopularBookGenreTaken(@RequestBody String genre) {
        return historyBookTakenRepository.getNotMostPopularGenre(genre);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/popular_repository", method = RequestMethod.GET)
    public List<Object[]> takeByMostPopularBookRepository() {
        return historyReservationRepository.getMostPopular();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/not_popular_repository", method = RequestMethod.GET)
    public List<Object[]> takeByNotPopularBookRepository() {
        return historyReservationRepository.getNotMostPopular();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/popular_author_repository", method = RequestMethod.POST)
    public List<Object[]> takeByMostPopularBookAuthorRepository(@RequestBody String author) {
        return historyReservationRepository.getMostPopularAuthor(author);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/not_popular_author_repository", method = RequestMethod.POST)
    public List<Object[]> takeByNotPopularBookAuthorRepository(@RequestBody String author) {
        return historyReservationRepository.getNotMostPopularAuthor(author);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/popular_genre_repository", method = RequestMethod.POST)
    public List<Object[]> takeByMostPopularBookGenreRepository(@RequestBody String genre) {
        return historyReservationRepository.getMostPopularGenre(genre);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/not_popular_genre_repository", method = RequestMethod.POST)
    public List<Object[]> takeByNotPopularBookGenreRepository(@RequestBody String genre) {
        return historyReservationRepository.getNotMostPopularGenre(genre);
    }
}
