package com.syzton.sunread.service.semester;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.dto.semester.SemesterDTO;
import com.syzton.sunread.model.semester.Semester;

public interface SemesterService {
	
    public SemesterDTO add(SemesterDTO added);
    
    public Semester deleteById(long id);

    public SemesterDTO update(SemesterDTO updated);

    public Semester findOne(Long id) ;

    Page<Semester> findAll(Pageable pageable);
}
