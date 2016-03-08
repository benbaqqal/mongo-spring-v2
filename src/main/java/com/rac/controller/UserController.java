package com.rac.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/user")
    public UserDetails user() {

	UserDetails user = null;
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	if (auth != null) {
	    Object principal = auth.getPrincipal();
	    if (principal instanceof UserDetails) {
		user = (UserDetails) principal;
	    }
	}

	return user;
    }
}
