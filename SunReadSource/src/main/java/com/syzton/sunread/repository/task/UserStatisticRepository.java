package com.syzton.sunread.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.user.UserStatistic;

/**
 * Created by jerry on 4/14/15.
 */
public interface UserStatisticRepository extends JpaRepository<UserStatistic,Long> {
}
