package com.syzton.sunread.repository.book.predicates;

import querydsl.com.syzton.sunread.model.book.QBook;

import com.mysema.query.types.Predicate;
import com.syzton.sunread.model.book.BookExtra;


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

//    public static Predicate findByExtra(BookExtra extra){
//        QBook book = QBook.book;
//        Predicate predicate = book.;
//        if(extra.getLevel()!= 0) // not all type
//            predicate = book.extra.level.eq(extra.getLevel());
//        else{
////            predicate = book.extra;
//        }
//        return book.extra.level.eq(extra.getLevel())
//                .andAnyOf(book.extra.language.eq(extra.getLanguage()))
//                .andAnyOf(book.extra.testType.eq(extra.getTestType()))
//                .andAnyOf(book.extra.literature.eq(extra.getLiterature()));
//    }
}
