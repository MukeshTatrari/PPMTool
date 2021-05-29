package com.ppm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ppm.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

	public Project findByProjectIdentifier(String projectIdentifier);

	@Override
	Iterable<Project> findAll();

	Iterable<Project> findByProjectLeader(String projectLeader);

}
