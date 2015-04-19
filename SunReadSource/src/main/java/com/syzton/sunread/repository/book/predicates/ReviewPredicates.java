package com.syzton.sunread.repository.book.predicates;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import com.syzton.sunread.dto.book.BookExtraDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.QBook;
import com.syzton.sunread.model.book.QReview;

/**
 */
public class ReviewPredicates {

    private static final int POINT_LEVET_0 = 0;
    private static final int POINT_LEVET_5 = 5;
    private static final int POINT_LEVET_10 = 10;
    private static final int POINT_LEVET_20 = 20;

    public static Predicate quickSearchContains(Book book) {
        QReview review = QReview.review;

        return review.book.eq(book);
    }

}
