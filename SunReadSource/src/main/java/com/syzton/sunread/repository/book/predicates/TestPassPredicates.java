package com.syzton.sunread.repository.book.predicates;

import com.mysema.query.types.Predicate;
import com.syzton.sunread.model.book.QRecommendation;
import com.syzton.sunread.model.book.QTestPass;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

/**
 */
public class TestPassPredicates {

    public static Predicate countTPDuringWeekly(Long bookId) {
        DateTime  today= DateTime.now().withTime(0,0,0,0);
        DateTime weekStart = today.withDayOfWeek(DateTimeConstants.MONDAY);
        DateTime weekEnd = today.withDayOfWeek(DateTimeConstants.SUNDAY);
        return countTPDuring(bookId, weekStart, weekEnd);
    }

    public static Predicate countTPDuringMonthly(Long bookId) {

        DateTime  today= DateTime.now().withTime(0,0,0,0);
        DateTime monthStart = today.withDayOfMonth(1);
        DateTime monthEnd = today.withDayOfMonth(1).plusMonths(1);
        return countTPDuring(bookId, monthStart, monthEnd);
    }

    public static Predicate countTPDuringYearly(Long bookId) {
        DateTime  today= DateTime.now().withTime(0,0,0,0);
        DateTime weekStart = today.withDayOfYear(1);
        DateTime weekEnd = today.withDayOfYear(1).plusYears(1);
        return countTPDuring(bookId,weekStart,weekEnd);
    }

    public static Predicate countTPDuring(Long bookId,DateTime start,DateTime end) {
        QTestPass testPass = QTestPass.testPass;
        return testPass.bookId.eq(bookId).and(testPass.creationTime.between(start, end));
    }


}
