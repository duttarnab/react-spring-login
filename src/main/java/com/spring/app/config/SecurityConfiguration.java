package com.spring.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.app.repository.UserRepository;
import com.spring.app.service.UserDetailsServiceImpl;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses= UserRepository.class)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception{

		auth.userDetailsService(userDetailsService)
		.passwordEncoder(getPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("**/secure/**").authenticated()
			.anyRequest().permitAll()
			.and().formLogin().loginPage("/login").successForwardUrl("/rest/secured/loginSuccess").permitAll()
			.and().logout().logoutSuccessUrl("/login?logout");

	}

	private PasswordEncoder getPasswordEncoder(){
		return new PasswordEncoder() {

			@Override
			public boolean matches(CharSequence arg0, String arg1) {
				// TODO Auto-generated method stub
				return arg0.toString().equals(arg1);
			}

			@Override
			public String encode(CharSequence charSeq) {
				// TODO Auto-generated method stub
				return charSeq.toString();
			}
		};
	}
}
