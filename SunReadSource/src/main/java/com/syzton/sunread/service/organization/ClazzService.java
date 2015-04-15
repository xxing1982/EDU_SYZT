package com.syzton.sunread.service.organization;

import java.util.List;

import com.syzton.sunread.dto.organization.ClazzDTO;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.user.Student;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public interface ClazzService {

    public Clazz add(ClazzDTO Clazz, Long id) throws NotFoundException;

    public Clazz deleteById(Long id) throws NotFoundException;

    public Clazz update(ClazzDTO updated) throws NotFoundException;

    public Clazz findById(Long id) throws NotFoundException;
  
    Page<Clazz> findAll(Pageable pageable) throws NotFoundException;

    Page<Clazz> findByCampus(Long campusId,Pageable pageable) throws NotFoundException;
    
    public List<Student> findAllStudentFromClazz(int clazzId)throws NotFoundException;
    
    public int getAveragePointsfromClass(int clazzId) throws NotFoundException;
    
    public int getAverageReadingBookFromClass(int clazzId) throws NotFoundException;
}
