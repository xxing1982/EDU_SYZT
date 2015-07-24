package com.syzton.sunread.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.syzton.sunread.model.security.Role;
import com.syzton.sunread.model.user.User;

public class UserUtil {
	public static boolean isPlatformAdmin(User user){
		if(user==null){return false;}
		Role superRole = new Role("ROLE_SYSTEM_SUPER_ADMIN");
		Role systemRole = new Role("ROLE_SYSTEM_ADMIN");
		if(user.hasRole(superRole)||user.hasRole(systemRole)){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isSchoolAdmin(User user){
		if(user==null){return false;}
		Role schoolRole = new Role ("ROLE_SCHOOLE_ADMIN");
		Role schoolSuperRole = new Role("ROLE_SCHOOLE_SUPER_ADMIN");
		if(user.hasRole(schoolRole)||user.hasRole(schoolSuperRole)){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isAdmin(User user){
		if(isSchoolAdmin(user)||isPlatformAdmin(user)){
			return true;
		}
		return false;
	}
	
	public static String getUser() {

		User principal = null;
		String userId = null;
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		if (authentication != null) {
			Object currentPrincipal = authentication.getPrincipal();
			if (currentPrincipal instanceof User) {
				principal = (User) currentPrincipal;
			}
		}

		if (principal != null) {
			userId = principal.getUserId();
		}

		return userId;
	}
	
	
}
