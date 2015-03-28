package com.syzton.sunread.repository.book.predicates;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import com.syzton.sunread.dto.book.BookExtraDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.QBook;
import com.syzton.sunread.model.book.QRecommendation;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

/**
 */
public class RecommendationPredicates {

    public static Predicate countRMDuringWeekly(Long bookId) {
        QRecommendation recommendation = QRecommendation.recommendation;
        DateTime  today= DateTime.now().withTime(0,0,0,0);
        DateTime weekStart = today.withDayOfWeek(DateTimeConstants.MONDAY);
        DateTime weekEnd = today.withDayOfWeek(DateTimeConstants.SUNDAY);
        return recommendation.bookId.eq(bookId).and(recommendation.creationTime.between(weekStart, weekEnd));
    }

    public static Predicate countRMDuringMonthly(Long bookId) {
        QRecommendation recommendation = QRecommendation.recommendation;
        DateTime  today= DateTime.now().withTime(0,0,0,0);
        DateTime monthStart = today.withDayOfMonth(1);
        DateTime monthEnd = today.withDayOfMonth(1).plusMonths(1);
        return recommendation.bookId.eq(bookId).and(recommendation.creationTime.between(monthStart, monthEnd));
    }

    public static Predicate countRMDuringYearly(Long bookId) {
        QRecommendation recommendation = QRecommendation.recommendation;
        DateTime  today= DateTime.now().withTime(0,0,0,0);
        DateTime weekStart = today.withDayOfYear(1);
        DateTime weekEnd = today.withDayOfYear(1).plusYears(1);
        return recommendation.bookId.eq(bookId).and(recommendation.creationTime.between(weekStart, weekEnd));
    }

}
