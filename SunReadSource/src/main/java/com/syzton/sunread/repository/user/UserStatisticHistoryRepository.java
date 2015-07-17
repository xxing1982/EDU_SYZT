package com.syzton.sunread.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syzton.sunread.model.user.UserStatisticHistory;

/**
 * Created by jerry on 3/16/15.
 */
@Repository
public interface UserStatisticHistoryRepository extends JpaRepository<UserStatisticHistory,Long>{
	
	UserStatisticHistory findByStudentIdAndSemesterId(Long studentId,Long semesterId);
}
