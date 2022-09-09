package com.rabbit.tracker.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rabbit.tracker.entity.LogEntity;
import com.rabbit.tracker.form.LogForm;
import com.rabbit.tracker.service.LogService;

@CrossOrigin("http://localhost:8081")
@RestController
public class Controller {

	@Autowired
	private LogService ls;

	@GetMapping("/api/checkLoginStatus")
	public String checkLoginStatus(Principal principal) {
		// このuidをidと一緒にテーブルに追加すれば、ユーザの登録になる？
		return principal.getName(); // Returns firebase uid
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
	public boolean edit(LogForm form, BindingResult result, Principal principal) {
		System.out.println(form);
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
		// firebase uidからユーザを検索してそのIDを返すことに変えるべき
		return ls.getAllLog(principal.getName());
	}

}
