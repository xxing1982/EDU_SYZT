package com.syzton.sunread.repository.coinhistory;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.syzton.sunread.model.coinhistory.CoinHistory;
import com.syzton.sunread.model.coinhistory.CoinHistory.CoinFrom;
import com.syzton.sunread.model.user.Student;

/**
 * @author chenty
 *
 */
public interface CoinHistoryRepository extends JpaRepository<CoinHistory,Long> {
	@Query("SELECT Distinct(b) FROM CoinHistory b WHERE b.fromId = (:fromId) AND b.coinFrom = (:coinFrom)")
	Page<CoinHistory> findByFromIdAndCoinFrom(@Param("fromId")Long fromId, @Param("coinFrom")CoinFrom coinFrom, Pageable pageable);
	
	@Query("SELECT Distinct(c) FROM CoinHistory c WHERE c.coinFrom = (:coinFrom) AND EXISTS(SELECT s FROM Student s WHERE s.clazzId = (:classId) AND s.id = c.student.id)")
	Page<CoinHistory> findByClassIdAndCoinFrom(@Param("classId")Long classId, @Param("coinFrom")CoinFrom coinFrom, Pageable pageable);
	
	@Query("SELECT Distinct(b) FROM CoinHistory b WHERE b.creationTime >=:startTime AND b.creationTime<=:endTime AND b.student=:student")
	ArrayList<CoinHistory> findBySemesterAndStudent(@Param("startTime")DateTime startTime, @Param("endTime")DateTime endTime, @Param("student")Student student);
}
