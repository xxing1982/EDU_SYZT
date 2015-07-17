package com.syzton.sunread.repository.user.predicates;

import com.mysema.query.types.Predicate;
import com.syzton.sunread.model.user.QStudent;

/**
 */
public class StudentPredicates {


    public static Predicate findAllStudentsInCampus(Long campusId) {
        QStudent student = QStudent.student;
        return student.campusId.eq(campusId);
    }


}
