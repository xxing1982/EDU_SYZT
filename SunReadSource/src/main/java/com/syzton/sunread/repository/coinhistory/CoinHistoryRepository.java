package com.syzton.sunread.repository.coinhistory;

import com.syzton.sunread.model.coinhistory.CoinHistory;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chenty
 *
 */
public interface CoinHistoryRepository extends JpaRepository<CoinHistory,Long> {
}
