package com.syzton.sunread.service.organization;

import com.syzton.sunread.dto.organization.EduGroupDTO;
import com.syzton.sunread.model.organization.EduGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */

public interface EduGroupService {

    public EduGroup add(EduGroupDTO EduGroup);

    public EduGroup deleteById(Long id);

    public EduGroup update(EduGroupDTO updated);

    public EduGroup findById(Long id);

    Page<EduGroup> findAll(Pageable pageable);
}
