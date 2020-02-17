package com.johanrivas.jlearning.Services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.johanrivas.jlearning.Dao.IUserDao;
import com.johanrivas.jlearning.Entities.Role;
import com.johanrivas.jlearning.Entities.User;

@Service
public class JPAUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserDao userDao;

	private org.slf4j.Logger logger = LoggerFactory.getLogger(JPAUserDetailsService.class);

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userDao.findByEmail(email);

		if (user == null) {
			logger.error("the user with the email " + email + "does not exist in the system");
			throw new UsernameNotFoundException("email: " + email + " does not exist in the system!");
		}

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role role : user.getRoles()) {
			logger.info("Role: ".concat(role.getAuthority()));
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}

		if (authorities.isEmpty()) {
			logger.error("the user does not have any role assigned");
			throw new UsernameNotFoundException("the user does not have any role assigned");
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				user.getEnable(), true, true, true, authorities);
	}
}
