package com.syzton.sunread.controller.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.syzton.sunread.model.user.User;
import com.syzton.sunread.service.user.UserService;
 
@Component
public class SecurityContextUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityContextUtil.class);
    
    private UserService userService;
    
    @Autowired
    public SecurityContextUtil( UserService userService) {
        this.userService = userService;
    }
    
    public   UserDetails getPrincipal() {
        LOGGER.debug("Getting principal from the security context");

        UserDetails principal = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object currentPrincipal = authentication.getPrincipal();
            if (currentPrincipal instanceof UserDetails) {
                principal = (UserDetails) currentPrincipal;
            }
        }

        return principal;
    }
    
    public User getUser(){
    	 LOGGER.debug("Getting principal from the security context");

         User principal = null;
         User user = null;
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
         if (authentication != null) {
             Object currentPrincipal = authentication.getPrincipal();
             if (currentPrincipal instanceof User) {
                 principal = (User) currentPrincipal;
             }
         }
    
         if(principal!=null){
        	 user = userService.findByUserId(principal.getUserId());
         }

         return user;
    }
}
