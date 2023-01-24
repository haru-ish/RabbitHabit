package com.rabbit.tracker.controller;

import java.security.Principal;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbit.tracker.entity.LogEntity;
import com.rabbit.tracker.form.LogForm;
import com.rabbit.tracker.service.LogService;

@RestController
public class Controller {

	@Autowired
	private LogService ls;

	// Check login user
	@GetMapping("/api/checkLoginUser")
	public boolean checkLoginUser(Principal principal) {
		Boolean result = ls.checkLoginUser(principal.getName());
		// when this userUid does not exist in DB
		if(!result) {
			// Add this userUid as new user in DB
			ls.addLoginUser(principal.getName());
		}
		return true;
	}

	// Add new log
	@PostMapping("/api/create")
	public int create(@Validated LogForm form, BindingResult result, Principal principal) {
		form.setUserUid(principal.getName());
		// Check for validations
		if (result.hasErrors()) {
			return 1;
		}
		ls.create(form);
		return form.getId();
	}

	// Update the log
	@PostMapping("/api/edit")
	public boolean edit(@Validated LogForm form, BindingResult result, Principal principal) {
		form.setUserUid(principal.getName());
		// Check for validations
		if (result.hasErrors()) {
			return false;
		}
		return ls.edit(form);
	}

	// Delete the log
	@PostMapping("/api/delete")
	public boolean delete(int id, Principal principal) {
		LogForm form = new LogForm();
		form.setId(id);
		form.setUserUid(principal.getName());
		return ls.delete(form);
	}

	// Get TodoList
	@GetMapping("/api/getAllLog")
	public List<LogEntity> getAllLog(Principal principal) {
		return ls.getAllLog(principal.getName());
	}

}
