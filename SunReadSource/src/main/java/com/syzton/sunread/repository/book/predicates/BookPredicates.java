package com.syzton.sunread.repository.book.predicates;

import com.mysema.query.BooleanBuilder;
import com.syzton.sunread.dto.book.BookExtraDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.QBook;

import com.mysema.query.types.Predicate;

/**
 */
public class BookPredicates {

    public static Predicate quickSearchContains(String searchTerm) {
        QBook book = QBook.book;
        return book.status.eq(Book.Status.valid).and(book.name.containsIgnoreCase(searchTerm)
                .or(book.isbn.containsIgnoreCase(searchTerm))
                .or(book.author.containsIgnoreCase(searchTerm)
                .or(book.publisher.containsIgnoreCase(searchTerm))));
    }

    public static Predicate findByCondition(BookExtraDTO condition){
        QBook book = QBook.book;

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(book.status.eq(Book.Status.valid));

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

        if(condition.getGrade()!=0){
            builder.and(book.extra.grade.eq(condition.getGrade()));
        }

        if(condition.getCategory()!=0){
            builder.and(book.extra.category.eq(condition.getCategory()));
        }

        if(condition.getAgeRange()!=0){
            builder.and(book.extra.ageRange.eq(condition.getAgeRange()));
        }


        return builder.getValue();
    }
}
