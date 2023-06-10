package com.ppm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ppm.domain.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	 Project findByProjectIdentifier(String projectIdentifier);

	@Override
	List<Project> findAll();

	List<Project> findByProjectLeader(String projectLeader);

}
