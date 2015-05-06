package com.syzton.sunread.repository.pointhistory;

import java.util.List;

import com.syzton.sunread.model.note.Note;
import com.syzton.sunread.model.pointhistory.PointHistory;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chenty
 *
 */
public interface PointHistoryRepository extends JpaRepository<PointHistory,Long> {
	List<PointHistory> findByStudent(Student student);
}
