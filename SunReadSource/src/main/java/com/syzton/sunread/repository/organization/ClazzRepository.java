package com.syzton.sunread.repository.organization;

import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.Clazz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public interface ClazzRepository extends JpaRepository<Clazz,Long> {
    Page<Clazz> findByCampus(Campus campus,Pageable pageable);

}
