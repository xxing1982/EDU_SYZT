package com.syzton.sunread.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syzton.sunread.model.security.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
	public Role findByName(String name);
}
