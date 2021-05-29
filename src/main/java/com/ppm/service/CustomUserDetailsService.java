package com.ppm.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.ppm.domain.User;
import com.ppm.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		if (ObjectUtils.isEmpty(user)) {
			throw new UsernameNotFoundException("User Not Found");
		}
		return user;
	}

	@Transactional
	public User findUserById(Long Id) {
		User user = userRepository.getById(Id);
		if (ObjectUtils.isEmpty(user)) {
			throw new UsernameNotFoundException("User Not Found");
		}
		return user;

	}

}
