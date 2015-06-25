package com.syzton.sunread.service.task;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.tag.Tag;
import com.syzton.sunread.model.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author jerry
 *
 */
public interface TaskService {

    /**
     * Adds a new tag entry.
     * @param added The information of the added tag entry.
     * @return  The added tag entry.
     */
    public Task add(Task added);

    /**
     * Deletes a tag entry.
     * @param id    The id of the deleted tag entry.
     * @return  The deleted tag entry.
     */
    public void deleteById(Long id) throws NotFoundException;

    /**
     * Returns a list of tag entries.
     * @return
     */
    public Task findByStudentIdAndSemesterId(Long studentId,Long semesterId);
//
//    public Page<Task> findByTeacherId(Long teacherId,Pageable pageable);


}
