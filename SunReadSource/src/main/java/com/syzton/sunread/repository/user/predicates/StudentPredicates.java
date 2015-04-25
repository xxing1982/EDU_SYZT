package com.syzton.sunread.repository.user.predicates;

import com.mysema.query.types.Predicate;
import com.syzton.sunread.model.book.QTestPass;
import com.syzton.sunread.model.organization.QCampus;
import com.syzton.sunread.model.user.QStudent;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

/**
 */
public class StudentPredicates {


    public static Predicate findAllStudentsInCampus(Long campusId) {
        QStudent student = QStudent.student;
        QCampus campus = QCampus.campus;

        return student.campusId.eq(campusId);
    }


}
