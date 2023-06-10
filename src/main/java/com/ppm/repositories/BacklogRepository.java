package com.ppm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ppm.domain.Backlog;

@Repository
public interface BacklogRepository extends JpaRepository<Backlog, Long> {

	Backlog findByProjectIdentifier(String Identifier);

}
