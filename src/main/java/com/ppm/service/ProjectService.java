package com.ppm.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ppm.domain.Backlog;
import com.ppm.domain.Project;
import com.ppm.domain.User;
import com.ppm.exceptions.ProjectIdExceptionHandler;
import com.ppm.repositories.BacklogRepository;
import com.ppm.repositories.ProjectRepository;
import com.ppm.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

	private final ProjectRepository repository;
	private final BacklogRepository backlogRepository;
	private final UserRepository userRepository;

	public Project saveOrUpdateProject(Project project, String userName) {

		try {

			if (!ObjectUtils.isEmpty(project.getId())) {
				findByProjectIdentifier(project.getProjectIdentifier(), userName);
			}

			User user = userRepository.findByUserName(userName);
			log.info("project :::", project);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

			if (ObjectUtils.isEmpty(project.getId())) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}

			if (!ObjectUtils.isEmpty(project.getId())) {
				project.setBacklog(
						backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}

			return repository.save(project);

		} catch (Exception e) {
			throw new ProjectIdExceptionHandler("Project Id " + project.getProjectIdentifier());
		}

	}

	public Project findByProjectIdentifier(String projectId, String userName) {
		Project project = repository.findByProjectIdentifier(projectId.toUpperCase());

		if (project == null) {
			throw new ProjectIdExceptionHandler("Project Id " + projectId + " does not exists");
		}
		if (!project.getProjectLeader().equals(userName)) {
			throw new ProjectIdExceptionHandler("Project " + projectId + "does not belong to your account");
		}

		return project;
	}

	public Iterable<Project> findAll(String userName) {
		return repository.findByProjectLeader(userName);
	}

	public void deleteProjectByIdentifier(String projectIdentier, String userName) {

		Project project = this.findByProjectIdentifier(projectIdentier.toUpperCase(), userName);

		repository.delete(project);
	}
}
