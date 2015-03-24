package com.syzton.sunread.repository.book.predicates;

import com.mysema.query.BooleanBuilder;
import com.syzton.sunread.dto.book.ConditionDTO;
import com.syzton.sunread.model.book.QBook;

import com.mysema.query.types.Predicate;

/**
 */
public class BookPredicates {

    public static Predicate quickSearchContains(String searchTerm) {
        QBook book = QBook.book;
        return book.name.containsIgnoreCase(searchTerm)
                .or(book.isbn.containsIgnoreCase(searchTerm))
                .or(book.author.containsIgnoreCase(searchTerm)
                .or(book.publisher.containsIgnoreCase(searchTerm)));
    }

    public static Predicate findByCondition(ConditionDTO condition){
        QBook book = QBook.book;

        BooleanBuilder builder = new BooleanBuilder();
        if(condition.getLevel()!=0){
            builder.and(book.extra.level.eq(condition.getLevel()));
        }

        if(condition.getLanguage()!=0){
            builder.and(book.extra.language.eq(condition.getLanguage()));
        }

        if(condition.getTestType()!=0){
            builder.and(book.extra.testType.eq(condition.getTestType()));
        }

        if(condition.getLiterature()!=0){
            builder.and(book.extra.literature.eq(condition.getLiterature()));
        }


        return builder.getValue();
    }
}
