package com.ppm.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppm.domain.ProjectTask;
import com.ppm.service.MapValidationErrorSevice;
import com.ppm.service.ProjectTaskService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/backlog")
@CrossOrigin
@Slf4j
public class BacklogController {

	private final ProjectTaskService projecTaskService;
	private final MapValidationErrorSevice validationService;

	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addProjTaskToBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String backlog_id, Principal principal) {

		log.info("projectTask info   :::::: ", projectTask.toString());
		ResponseEntity<?> errors = validationService.mapValidations(result);
		if (errors != null) {
			return errors;
		}

		return new ResponseEntity<ProjectTask>(
				projecTaskService.addProjectTask(backlog_id.toUpperCase(), projectTask, principal.getName()),
				HttpStatus.CREATED);
	}

	@GetMapping("/{backlog_id}")
	public ResponseEntity<Iterable<ProjectTask>> getProjectbackLog(@PathVariable String backlog_id,
			Principal principal) {

		return new ResponseEntity<Iterable<ProjectTask>>(
				projecTaskService.findBacklogById(backlog_id, principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/{backlog_id}/{PTID}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String PTID,
			Principal principal) {
		return new ResponseEntity<ProjectTask>(
				projecTaskService.findProjTaskBySequence(backlog_id, PTID, principal.getName()), HttpStatus.OK);
	}

	@PatchMapping("/{backlog_id}/{PTID}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String backlog_id, @PathVariable String PTID, Principal principal) {

		log.info("projectTask info   :::::: ", projectTask.toString());
		ResponseEntity<?> errors = validationService.mapValidations(result);
		if (errors != null) {
			return errors;
		}
		return new ResponseEntity<ProjectTask>(
				projecTaskService.updateProjectTask(projectTask, backlog_id, PTID, principal.getName()), HttpStatus.OK);

	}

	@DeleteMapping("/{backlog_id}/{PTID}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String PTID,
			Principal principal) {

		projecTaskService.deleteProjectTask(backlog_id, PTID, principal.getName());
		return new ResponseEntity<String>("project Task " + PTID + " is deleted Successfully", HttpStatus.OK);

	}
}
