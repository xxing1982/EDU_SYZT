package com.syzton.sunread.repository.fish;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.fish.Fish;

/**
 * Created by jerry on 4/21/15.
 */
public interface FishRepository extends JpaRepository<Fish,Long>{
}
