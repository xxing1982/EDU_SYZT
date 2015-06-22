package com.syzton.sunread.repository.store;

import com.syzton.sunread.model.store.Gift;
import com.syzton.sunread.model.tag.BookTag;
import com.syzton.sunread.model.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface GiftRepository extends JpaRepository<Gift,Long> {

    Page<Gift> findByCampusId(Pageable pageable ,Long schoolId);
}
