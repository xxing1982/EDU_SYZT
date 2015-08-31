package com.syzton.sunread.repository.organization;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.organization.ClazzSumStatistic;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public interface ClazzRepository extends JpaRepository<Clazz,Long> {
    Page<Clazz> findByCampus(Campus campus,Pageable pageable);

    @Query("select count(s.id) from Student s where s.clazzId = :clazzId and s.campusId = :campusId")
    Number countStudentsInClazz(@Param("clazzId") long clazzId , @Param("campusId")long campusId);

    @Query("select new com.syzton.sunread.model.organization.ClazzSumStatistic(avg(cs.clazzStatistic.avgReads),max(cs.clazzStatistic.avgReads),min(cs.clazzStatistic.avgReads)," +
            "avg(cs.clazzStatistic.avgReadWords),max(cs.clazzStatistic.avgReadWords),min(cs.clazzStatistic.avgReadWords)," +
            "avg(cs.clazzStatistic.avgCoin),max(cs.clazzStatistic.avgCoin),min(cs.clazzStatistic.avgCoin)) from Clazz cs where cs.grade = :grade GROUP BY cs.grade ")
    ClazzSumStatistic getSumStatisticClazz(@Param("grade") int grade);

    List<Clazz> findByGrade(int grade);
    
    @Query("select c from Clazz c where c.campus.id= :campusId")
    List<Clazz> findByCampus(@Param("campusId")long campusId);
    
    Clazz findByName(String clazzName);

    Clazz findByNameAndCampus(String name,Campus campus);
}
