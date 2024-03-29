package com.rabbit.tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rabbit.tracker.entity.LogEntity;
import com.rabbit.tracker.form.LogForm;
import com.rabbit.tracker.mapper.LogMapper;

@Transactional
@Service
public class LogService {
	@Autowired
	private LogMapper logMapper;

	// Check login user
	public Boolean checkLoginUser(String userUid) {
		return this.logMapper.checkLoginUser(userUid);
	}
	// Add login user
	public int addLoginUser(String userUid) {
		return this.logMapper.addLoginUser(userUid);
	}
	// Get All Log info
	public List<LogEntity> getAllLog(String userUid) {
		return this.logMapper.getAllLog(userUid);
	}
	// Add new log 
	public int create(LogForm form) {
		return this.logMapper.create(form);
	}
	// Delete the log
	public boolean delete(LogForm form) {
		return this.logMapper.delete(form);
	}
    // Edit the log
	public boolean edit(LogForm form) {
		return this.logMapper.edit(form);
	}
	// For this class's test
	public LogService(LogMapper testMapper) {
		this.logMapper = testMapper;
	}

    
	
	
	

}

