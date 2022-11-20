package com.bcoronel.inventory.security;

import static org.springframework.http.HttpMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bcoronel.inventory.security.filter.JwtRequestFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	  @Autowired
	  private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	
	  @Override
	  @Bean
	  public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	  }
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.httpBasic();
		http.csrf().disable()
		.authorizeHttpRequests()
		//.antMatchers(POST, "/userservice/addRoleToUser/**").permitAll()
		//.antMatchers(POST,"/userservice/**").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers(POST,"/userservice/login/**").permitAll()
		.antMatchers(POST,"/personservice/newAdmin/**").permitAll()
		.antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
		.permitAll()
		.anyRequest().authenticated();
		 http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		/*
		//http.authorizeRequests().antMatchers(POST, "/userservice/addRoleToUser/**").permitAll();
		http.authorizeHttpRequests().anyRequest().authenticated().and().httpBasic();
		http.csrf().disable();
		http.authorizeRequests().antMatchers(POST, "/userservice/addRoleToUser/**").permitAll();
		*/
	}

}
