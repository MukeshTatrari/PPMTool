package com.ppm.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ppm.domain.Backlog;
import com.ppm.domain.ProjectTask;
import com.ppm.exceptions.ProjectNotFoundException;
import com.ppm.repositories.BacklogRepository;
import com.ppm.repositories.ProjectTaskRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectTaskService {

	private final BacklogRepository backlogRepository;
	private final ProjectTaskRepository projectTaskRepository;
	private final ProjectService projectService;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String userName) {

		try {
			Backlog backlog = projectService.findByProjectIdentifier(projectIdentifier, userName).getBacklog();
			if (ObjectUtils.isEmpty(backlog)) {
				throw new ProjectNotFoundException("Project Not Found");
			}

			projectTask.setBacklog(backlog);
			Integer backlogSequence = backlog.getPTSequence() + 1;
			backlog.setPTSequence(backlogSequence);
			projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);

			if (ObjectUtils.isEmpty(projectTask.getPriority()) || projectTask.getPriority() == 0) {
				projectTask.setPriority(3);
			}
			if (ObjectUtils.isEmpty(projectTask.getStatus())) {
				projectTask.setStatus("TO_DO");
			}

			return projectTaskRepository.save(projectTask);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<ProjectTask> findBacklogById(String backlog_id, String userName) {
		projectService.findByProjectIdentifier(backlog_id, userName);
		List<ProjectTask> projects = projectTaskRepository.findByProjectIdentifier(backlog_id);
		if (ObjectUtils.isEmpty(projects)) {
			throw new ProjectNotFoundException("Project with Id " + backlog_id + "  does not exists");
		} else {
			return projects;
		}
	}

	public ProjectTask findProjTaskBySequence(String backlog_id, String PTID,String userName) {
		
		projectService.findByProjectIdentifier(backlog_id, userName);
		ProjectTask task = projectTaskRepository.findByProjectSequence(PTID);
		if (ObjectUtils.isEmpty(task)) {
			throw new ProjectNotFoundException("Project Task  " + PTID + "  does not exists");
		}
		if (!task.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException(
					"Project Task  " + PTID + "  does not exists in " + backlog_id + " Project");
		}
		return task;
	}

	public ProjectTask updateProjectTask(ProjectTask projectTask, String backlog_id, String PTID,String userName) {

		ProjectTask task = this.findProjTaskBySequence(backlog_id, PTID,userName);
		return projectTaskRepository.save(projectTask);
	}

	public void deleteProjectTask(String backlog_id, String PTID,String userName) {

		ProjectTask task = this.findProjTaskBySequence(backlog_id, PTID,userName);
		projectTaskRepository.delete(task);

	}

}
