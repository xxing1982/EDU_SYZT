package com.syzton.sunread.service.user;

import com.syzton.sunread.model.user.Analyst;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by jerry on 6/13/15.
 */
public interface AnalystService {

    void add(Analyst analyst);

    void delete(Long Id);

    Analyst findByUserId(String userId);

    Page<Analyst> findByCampusId(Pageable pageable,long campusId);

    Page<Analyst> findByEduGroupId(Pageable pageable,long eduGroupId);

    Page<Analyst> findBySchoolDistrictId(Pageable pageable,long schoolDistrictId);

    Page<Analyst> findByRegionId(Pageable pageable,long regionId);

}
