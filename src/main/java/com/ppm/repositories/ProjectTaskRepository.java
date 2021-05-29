package com.ppm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ppm.domain.ProjectTask;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

	public Iterable<ProjectTask> findByProjectIdentifier(String projectIdentifier);

	ProjectTask findByProjectSequence(String projectSequence);

}
