package com.ppm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ppm.domain.ProjectTask;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {

	public List<ProjectTask> findByProjectIdentifier(String projectIdentifier);

	ProjectTask findByProjectSequence(String projectSequence);

}
