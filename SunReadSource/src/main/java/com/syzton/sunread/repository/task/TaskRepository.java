package com.syzton.sunread.repository.task;

import com.syzton.sunread.model.tag.Tag;
import com.syzton.sunread.model.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jerry
 *
 */
public interface TaskRepository extends JpaRepository<Task,Long> {


}
