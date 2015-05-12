package com.syzton.sunread.repository.recommend;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.recommend.Recommend;

/**
 * @author Morgan-Leon
 * @Date 2015年5月12日
 * 
 */
public interface RecommendRepository extends JpaRepository<Recommend, Long> {

}
