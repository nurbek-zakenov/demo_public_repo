package com.example.iasback.security.services;


import com.example.iasback.models.User;
//import com.example.iasback.repository.UserRepository;
import com.example.iasback.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserMapper userMapper;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.findByUsername(username);

		if(user==null){
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}

		return  UserDetailsImpl.build(user);
	}

}
