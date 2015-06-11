package com.syzton.sunread.repository.user;

import com.syzton.sunread.model.security.Role;
import com.syzton.sunread.model.user.Admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>{

    Page<Admin> findByCampusId(Long campusId,Pageable pageable);
    
    Page<Admin> findByCampusIdAndSuperAdmin(Long campusId,boolean isSuperAdmin,Pageable pageable);

	Admin findByUserId(String userId);

}
