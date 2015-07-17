package com.syzton.sunread.repository.store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syzton.sunread.model.store.Gift;

/**
 *
 */
@Repository
public interface GiftRepository extends JpaRepository<Gift,Long> {

    Page<Gift> findByCampusId(Pageable pageable, Long campusId);
}
