package com.syzton.sunread.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by jerry on 4/16/15.
 */
@Component
public class StatisticTask {
    @Scheduled(fixedDelay = 1000l)
    public void excuteStatistic(){
        System.out.println("timer test........");
    }
}
