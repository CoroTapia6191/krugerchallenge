package com.bcoronel.inventory.user.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcoronel.inventory.models.Userkrug;
import com.bcoronel.inventory.user.repo.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailServiceKrug implements UserDetailsService{
	@Autowired
	private IUserRepository userepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Userkrug user = userepository.findById(username).get();
		if (user == null) {

			throw new UsernameNotFoundException("Usuario no encontrado!!!");
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		UserDetails userdet = new User(user.getUsername(), user.getPassword(), authorities);
		return userdet;
	}
	

}
