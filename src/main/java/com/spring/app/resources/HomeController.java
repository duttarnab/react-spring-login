package com.spring.app.resources;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.model.Role;
import com.spring.app.model.User;

@RequestMapping("/rest")
@RestController
public class HomeController {

	@RequestMapping("/login")
	public ModelAndView loginPage(){
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		System.out.println("--->"+SecurityContextHolder.getContext().getAuthentication().getName());
		return model;
	}

	@RequestMapping(value = "/secured/loginSuccess", method = RequestMethod.POST)
	public User loginSuccess(){

		System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

		User user = new User();
		Set<Role> roles = new HashSet<Role>();
		user.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		roles.addAll((Collection<? extends Role>) SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		user.setRole(roles);
		user.setIsActive(1);

		return user;
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/secured/home")
	public String homePage(){
		return "home";
	}

	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping("/secured/user")
	public String userPage(){
		return "user page";
	}
	
	@RequestMapping("/noLogin")
	public String noLogin(){
		return "user page";
	}
}
