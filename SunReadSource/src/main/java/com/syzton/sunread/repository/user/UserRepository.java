package com.syzton.sunread.repository.user;

import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jerry on 3/16/15.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	
	User findByUserId(String userId);
}
