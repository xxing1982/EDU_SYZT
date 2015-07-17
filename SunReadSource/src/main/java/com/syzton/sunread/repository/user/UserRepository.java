package com.syzton.sunread.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syzton.sunread.model.user.User;

/**
 * Created by jerry on 3/16/15.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	
	User findByUserId(String userId);
}
