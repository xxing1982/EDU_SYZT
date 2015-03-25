package com.syzton.sunread.repository.pointhistory;

import com.syzton.sunread.model.pointhistory.PointHistory;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chenty
 *
 */
public interface PointHistoryRepository extends JpaRepository<PointHistory,Long> {
}
