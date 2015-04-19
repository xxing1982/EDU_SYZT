package com.syzton.sunread.repository.book.predicates;

import com.mysema.query.BooleanBuilder;
import com.syzton.sunread.dto.book.BookExtraDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.QBook;

import com.mysema.query.types.Predicate;

/**
 */
public class BookPredicates {

    private static final int POINT_LEVET_0 = 0;
    private static final int POINT_LEVET_5 = 5;
    private static final int POINT_LEVET_10 = 10;
    private static final int POINT_LEVET_20 = 20;

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
        if(condition.getSearchTerm()!=null && !"".equals(condition.getSearchTerm())){
            String searchTerm = condition.getSearchTerm();
            builder.and(book.name.containsIgnoreCase(searchTerm)
                    .or(book.isbn.containsIgnoreCase(searchTerm))
                    .or(book.author.containsIgnoreCase(searchTerm)
                            .or(book.publisher.containsIgnoreCase(searchTerm))));
        }

        if(condition.getLevel()!=0){
            builder.and(book.extra.level.eq(condition.getLevel()));
        }

        if(condition.getLanguage()!=0){
            builder.and(book.extra.language.eq(condition.getLanguage()));
        }

        if(condition.getTestType()!=0){
            switch (condition.getTestType()){
                case 1:
                    builder.and(book.extra.hasVerifyTest.eq(true));
                    break;
                case 2:
                    builder.and(book.extra.hasWordTest.eq(true));
                    break;
                case 3:
                    builder.and(book.extra.hasThinkTest.eq(true));
                    break;
            }

        }

        if(condition.getResource()!=0){
            switch (condition.getTestType()){
                case 1:
                    builder.and(book.extra.hasEbook.eq(true));
                    break;
                case 2:
                    builder.and(book.extra.hasRadio.eq(true));
                    break;
                case 3:
                    builder.and(book.extra.hasVideo.eq(true));
                    break;
            }

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
        if(condition.getPointRange()!=0){
            switch (condition.getPointRange()){
                case 1:
                    builder.and(book.point.goe(POINT_LEVET_0).and(book.point.loe(POINT_LEVET_5)));
                    break;
                case 2:
                    builder.and(book.point.gt(POINT_LEVET_5).and(book.point.loe(POINT_LEVET_10)));
                    break;
                case 3:
                    builder.and(book.point.gt(POINT_LEVET_10).and(book.point.loe(POINT_LEVET_20)));
                    break;
                case 4:
                    builder.and(book.point.gt(POINT_LEVET_20));
                    break;
            }

        }


        return builder.getValue();
    }
}
