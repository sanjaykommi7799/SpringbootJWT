package com.sanjay.SecurityJWT.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sanjay.SecurityJWT.DAO.UserDAO;
import com.sanjay.SecurityJWT.Entity.User;
@Service
public class IUserService implements UserService,UserDetailsService{

	@Autowired
	private UserDAO dao;
	
	//adding bcryptpasswordencoder for encoding the password before saving into database
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public Integer save(User user) {
		//here we have encoded teh password befoer saving it db
		user.setPassword(encoder.encode(user.getPassword()));
		return dao.save(user).getId();
	}

	@Override
	public Optional<User> findByUsername(String username) {
		// TODO Auto-generated method stub
		return dao.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> opt=findByUsername(username);
		if(opt.isEmpty())
		{
		throw new UsernameNotFoundException("user is not exst");
		}
		//read user from DB
		User user=opt.get();
		//List<GrantedAuthority> a=user.getRoles().stream().map(null)
		return new org.springframework.security.core.userdetails.User
				(username,
				user.getPassword(),
				user.getRoles().stream().map((role)->new SimpleGrantedAuthority(role))
				.collect(Collectors.toList())
				
				);
		
	}

}
