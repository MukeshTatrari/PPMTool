package com.ppm.service;

import com.ppm.domain.User;
import com.ppm.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    public User findUserById(Long Id) {
        User user = userRepository.findById(Id).orElse(null);
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return user;

    }

}
