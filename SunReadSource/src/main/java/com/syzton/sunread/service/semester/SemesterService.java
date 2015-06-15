package com.syzton.sunread.service.semester;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.dto.semester.SemesterDTO;
import com.syzton.sunread.model.semester.Semester;

public interface SemesterService {
    
    public Semester deleteById(long id);

    public SemesterDTO update(SemesterDTO updated);

    public Semester findOne(Long id) ;

    Page<Semester> findAll(Pageable pageable);

	/**
	 * @param time
	 * @return
	 */
	public Semester findByTime(DateTime time);


	/**
	 * @param studentId
	 * @return
	 */
	ArrayList<Semester> findByStudentId(Long studentId);

	/**
	 * @param added
	 * @param campusId
	 * @return
	 */
	SemesterDTO add(SemesterDTO added, Long campusId);
}
