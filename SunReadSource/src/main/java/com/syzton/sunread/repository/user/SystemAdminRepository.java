package com.syzton.sunread.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.user.SystemAdmin;

public interface SystemAdminRepository  extends JpaRepository<SystemAdmin,Long>{ 
	    
	  Page<SystemAdmin> findBySuperAdmin(boolean isSuperAdmin,Pageable pageable);
	    
	  SystemAdmin findByUserId(String userId);
}
