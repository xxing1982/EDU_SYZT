package com.syzton.sunread.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.task.Task;

/**
 * @author jerry
 *
 */
public interface TaskRepository extends JpaRepository<Task,Long> {

    Task findByStudentIdAndSemesterId(Long studentId,Long semesterId);
//
//    Page<Task> findByTeacherId(long teacherId,Pageable pageable);
}
