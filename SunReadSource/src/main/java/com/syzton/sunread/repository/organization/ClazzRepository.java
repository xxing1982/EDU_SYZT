package com.syzton.sunread.repository.organization;

import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.Clazz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public interface ClazzRepository extends JpaRepository<Clazz,Long> {
    Page<Clazz> findByCampus(Campus campus,Pageable pageable);

    @Query("select count(s.id) from Student s where s.clazzId = :clazzId and s.campusId = :campusId")
    Number countStudentsInClazz(@Param("clazzId") long clazzId , @Param("campusId")long campusId);


}
