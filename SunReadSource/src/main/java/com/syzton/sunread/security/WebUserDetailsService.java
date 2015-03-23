package com.syzton.sunread.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.syzton.sunread.model.user.User;
import com.syzton.sunread.service.user.UserService;

@Component
public class WebUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserService userService; 
	
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		User user = userService.getSingleUser(userName);
        if (user == null) {
            throw new NotFoundException("");
        }
        Long userId = user.getId();
        String passWord = user.getPassword();
        boolean userEnabled = user.getStats() == 1;
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        Set<Role> userRoles = user.getRoles();
        for (Role userRole : userRoles) {
            // 和WebSecurityMetadataSource中的SecurityConfig参数对应
        	SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"
                    + userRole.getId());
            authorities.add(authority);
        }
        // 创建 UserDetails 对象
        WebUserDetails webUserDetails = new WebUserDetails(userId, userName,
                passWord, userEnabled, authorities);
 
        return webUserDetails;
	}

}
