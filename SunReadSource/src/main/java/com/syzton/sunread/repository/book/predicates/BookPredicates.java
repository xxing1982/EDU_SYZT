package com.syzton.sunread.repository.book.predicates;

import querydsl.com.syzton.sunread.model.book.QBook;

import com.mysema.query.types.Predicate;

/**
 * @author Petri Kainulainen
 */
public class BookPredicates {

    public static Predicate quickSearchContains(String searchTerm) {
        QBook book = QBook.book;
        return book.name.containsIgnoreCase(searchTerm)
                .or(book.isbn.containsIgnoreCase(searchTerm))
                .or(book.author.containsIgnoreCase(searchTerm)
                .or(book.publisher.containsIgnoreCase(searchTerm)));
    }
}
