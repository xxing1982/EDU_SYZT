package com.syzton.sunread.repository.task;

import com.syzton.sunread.model.tag.Tag;
import com.syzton.sunread.model.task.Task;
import com.syzton.sunread.model.user.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jerry
 *
 */
public interface TaskRepository extends JpaRepository<Task,Long> {

    Task findByStudentIdAndSemesterId(Long studentId,Long semesterId);
//
//    Page<Task> findByTeacherId(long teacherId,Pageable pageable);
}
