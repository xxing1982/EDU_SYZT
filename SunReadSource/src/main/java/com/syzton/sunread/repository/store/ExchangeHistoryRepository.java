package com.syzton.sunread.repository.store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syzton.sunread.model.store.ExchangeHistory;
import com.syzton.sunread.model.store.Gift;

/**
 *
 */
@Repository
public interface ExchangeHistoryRepository extends JpaRepository<ExchangeHistory,Long> {

    Page<ExchangeHistory> findByStudentId(Pageable pageable,long studentId);

    ExchangeHistory findByStudentIdAndGift(long studentId,Gift gift);
}
