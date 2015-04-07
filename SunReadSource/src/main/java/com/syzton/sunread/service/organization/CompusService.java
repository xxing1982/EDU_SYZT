package com.syzton.sunread.service.organization;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.dto.organization.CompusDTO;
import com.syzton.sunread.model.organization.Compus;

/**
 * @author Morgan-Leon
 * @Date 2015年4月6日
 * 
 */
public interface CompusService {

	/**
	 * @param add
	 * @param region
	 * @param school
	 * @return
	 */
	Compus add(CompusDTO add, Long region, Long school);
	
    public Compus deleteById(Long id) throws NotFoundException;

    public Compus update(CompusDTO updated) throws NotFoundException;

    public Compus findById(Long id) throws NotFoundException;

    Page<Compus> findAll(Pageable pageable) throws NotFoundException;

	
}
