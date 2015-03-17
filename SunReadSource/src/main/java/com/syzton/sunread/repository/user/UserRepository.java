package com.syzton.sunread.repository.user;

import com.syzton.sunread.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public interface UserRepository extends JpaRepository<User,Long> {
}
