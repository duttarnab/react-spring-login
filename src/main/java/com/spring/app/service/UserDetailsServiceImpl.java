package com.spring.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.app.model.CustomUserDetails;
import com.spring.app.model.User;
import com.spring.app.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		Optional<User> optionalUser = userRepository.findByEmail(email);
		optionalUser.ifPresent(user -> new CustomUserDetails(user));
		optionalUser.orElseThrow(() -> new UsernameNotFoundException("Email Not Found!!"));
		CustomUserDetails customUserDetails = optionalUser.map(CustomUserDetails::new).get();
		return customUserDetails;
	}

}
