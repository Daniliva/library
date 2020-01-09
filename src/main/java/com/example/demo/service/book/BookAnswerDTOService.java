package com.example.demo.service.book;

import com.example.demo.dto.book.BookAnswerDTO;
import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalBook;

public class BookAnswerDTOService {
    public static BookAnswerDTO createBookAnswerDTO(JournalBook journalBook, Book book, Long countReservation) {
        BookAnswerDTO bookAnswerDTO = new BookAnswerDTO();
        bookAnswerDTO.setId(book.getId());
        bookAnswerDTO.setAuthor(book.getAuthor());
        bookAnswerDTO.setGenre(book.getGenre());
        bookAnswerDTO.setName(book.getName());
        bookAnswerDTO.setGenre(book.getGenre());
        bookAnswerDTO.setCount(journalBook.getCount());
        bookAnswerDTO.setCountTake(journalBook.getCountTake());
        bookAnswerDTO.setCountReservation(countReservation);
        return bookAnswerDTO;
    }
}
