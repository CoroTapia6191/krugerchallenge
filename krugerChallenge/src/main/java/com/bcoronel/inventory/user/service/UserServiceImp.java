package com.bcoronel.inventory.user.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcoronel.inventory.models.AuthenticationReq;
import com.bcoronel.inventory.models.Roles;
import com.bcoronel.inventory.models.TokenJWT;
import com.bcoronel.inventory.models.UserDtoChPasswor;
import com.bcoronel.inventory.models.Userkrug;
import com.bcoronel.inventory.role.repo.IRoleRepository;
import com.bcoronel.inventory.security.util.JwtUtilService;
import com.bcoronel.inventory.user.repo.IUserRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImp implements IUserService {
	@Autowired
	private IUserRepository userepository;
	@Autowired
	private IRoleRepository rolerepo;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService usuarioDetailsService;
	@Autowired
	private JwtUtilService jwtutil;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public boolean createuser(Userkrug usr) {
		userepository.save(usr);
		return true;
	}

	@Override
	public void addUserRole(String username, String rolname) {
		Userkrug user = userepository.findById(username).get();
		Roles role = rolerepo.findByName(rolname);
		user.getRoles().add(role);
	}
	
	@Override
	public void changepassword(UserDtoChPasswor user) {
		Userkrug currentusr = userepository.findById(user.getUsername()).get();
		if(currentusr==null) {
			throw new RuntimeException("User not found");
		}
		currentusr.setPassword(encoder.encode(user.getNewpassword()));
		userepository.save(currentusr);
	}

	@Override
	public TokenJWT authenticateUSer(AuthenticationReq credentials) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
		final UserDetails userdetils = usuarioDetailsService.loadUserByUsername(credentials.getUsername());

		final String tokenjwt = jwtutil.generateToken(userdetils);

		return new TokenJWT(tokenjwt);
	}

}
