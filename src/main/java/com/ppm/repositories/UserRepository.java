package com.ppm.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ppm.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User findByUserName(String userName);
	
	List<User> findByFullName(String fullName);

	User getById(Long id);

}
