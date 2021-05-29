package com.ppm.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppm.domain.Project;
import com.ppm.service.MapValidationErrorSevice;
import com.ppm.service.ProjectService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/project")
@RequiredArgsConstructor
@CrossOrigin
public class ProjectController {
	private final ProjectService projectService;
	private final MapValidationErrorSevice validationService;

	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody(required = true) Project project,
			BindingResult result, Principal principal) {
		log.info("project info   :::::: ", project.toString());
		ResponseEntity<?> errors = validationService.mapValidations(result);
		if (errors != null) {
			return errors;
		}
		return new ResponseEntity<Project>(projectService.saveOrUpdateProject(project, principal.getName()),
				HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId,Principal principal) {
		return new ResponseEntity<Project>(projectService.findByProjectIdentifier(projectId,principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/all")
	public Iterable<Project> findAll(Principal principal) {

		Iterable<Project> projects = projectService.findAll(principal.getName());
		return projects;
	}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProjectById(@PathVariable String projectId,Principal principal) {
		projectService.deleteProjectByIdentifier(projectId,principal.getName());

		return new ResponseEntity<String>("Project with " + projectId + " was deleted ", HttpStatus.OK);
	}

}
