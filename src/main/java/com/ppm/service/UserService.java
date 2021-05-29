package com.ppm.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ppm.domain.User;
import com.ppm.exceptions.UserNameAlreadyExistException;
import com.ppm.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public User saveUser(User user) {

		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setUserName(user.getUsername());
			user.setConfirmPassword(null);
			return userRepository.save(user);
		} catch (Exception e) {
			throw new UserNameAlreadyExistException("Username " + user.getUsername() + " is already exists");
		}
	}
	
	public User searchByUserName(String userName) {
		
	}

}
