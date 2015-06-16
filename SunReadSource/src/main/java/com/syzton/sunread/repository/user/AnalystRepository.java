package com.syzton.sunread.repository.user;

import com.syzton.sunread.model.user.Analyst;
import com.syzton.sunread.model.user.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jerry on 3/16/15.
 */
@Repository
public interface AnalystRepository extends JpaRepository<Analyst,Long>{

    Page<Analyst> findByCampusId(Pageable pageable,Long campusId) ;

    Page<Analyst> findBySchoolDistrictId( Pageable pageable,Long schoolDistrictId);

    Page<Analyst> findByRegionId( Pageable pageable,Long regionId);

    Page<Analyst> findByEduGroupId( Pageable pageable,Long regionId);


	public Analyst findByUserId(String userId);

}
