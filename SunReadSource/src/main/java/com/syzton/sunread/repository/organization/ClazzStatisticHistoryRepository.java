package com.syzton.sunread.repository.organization;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.organization.ClazzStatisticHistory;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public interface ClazzStatisticHistoryRepository extends JpaRepository<ClazzStatisticHistory,Long> {
    ClazzStatisticHistory findByClazzIdAndSemesterId(Long clazzId, Long semesterId);

}
