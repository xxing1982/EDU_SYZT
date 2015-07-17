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

/**
 * @author chenty
 *
 */
public interface CoinHistoryRepository extends JpaRepository<CoinHistory,Long> {
	@Query("SELECT Distinct(b) FROM CoinHistory b WHERE b.fromId = (:fromId) AND b.coinFrom = (:coinFrom)")
	Page<CoinHistory> findByFromIdAndCoinFrom(@Param("fromId")Long fromId, @Param("coinFrom")CoinFrom coinFrom, Pageable pageable);
	
	@Query("SELECT Distinct(b) FROM CoinHistory b WHERE b.creationTime between (:startTime) AND (:endTime)")
	ArrayList<CoinHistory> findBySemester(@Param("startTime")DateTime startTime, @Param("endTime")DateTime endTime);
}
