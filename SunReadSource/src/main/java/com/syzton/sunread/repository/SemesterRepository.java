package com.syzton.sunread.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.semester.Semester;

public interface SemesterRepository extends JpaRepository<Semester,Long>{

}
