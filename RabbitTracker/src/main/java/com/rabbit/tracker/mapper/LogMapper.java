package com.rabbit.tracker.mapper;

import com.rabbit.tracker.entity.LogEntity;
import com.rabbit.tracker.form.LogForm;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {
	
	// Check login user
	Boolean checkLoginUser(String userUid);
	// Add login user
	int addLoginUser(String userUid);
	// Get All Log info
	List<LogEntity> getAllLog(String userUid);
    // Add new log
	int create(LogForm form);
	// Delete the log
	boolean delete(LogForm form);
	// Edit the log
	boolean edit(LogForm form);
	
	

}
