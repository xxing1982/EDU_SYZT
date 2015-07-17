package com.syzton.sunread.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.user.Parent;

/**
 * Created by jerry on 3/22/15.
 */
public interface ParentRepository extends JpaRepository<Parent,Long> {
}
