package com.rabbit.tracker.service;

import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import com.rabbit.tracker.entity.LogEntity;
import com.rabbit.tracker.form.LogForm;
import com.rabbit.tracker.mapper.LogMapper;

public class LogServiceTest {

	LogMapper logMapper;
	LogService logService;

	private String existingUser = "sample1234test";
	private String newUser = "sample9999test";

	@BeforeEach
	void setUp() {
		// Create mock for LogMapper
		logMapper = mock(LogMapper.class);
		// Create LogService's instance using mock of LogMapper
		logService = new LogService(logMapper);
	}

	// Check checkLoginUser() in LogMapper return true and is called once
	@Test
	void checkExistingUserReturnTrue() {
		when(logMapper.checkLoginUser(existingUser)).thenReturn(true);
		boolean result = logService.checkLoginUser(existingUser);
		assertTrue(result, "Error: The boolean expected true, but result of test is false");
		verify(logMapper, times(1)).checkLoginUser(existingUser);
	}

	// Check addLoginUser() in LogMapper return 1 and is called once
	@Test
	void checkAddLoginUserReturn1() {
		when(logMapper.addLoginUser(newUser)).thenReturn(1);
		int result = logService.addLoginUser(newUser);
		assertEquals(result, 1, "Error: The result is expected 1, but actual is not");
		verify(logMapper, times(1)).addLoginUser(newUser);

	}

	// Check getAllLog() in LogMapper return 1 list and is called once
	@Test
	void checkGet1list() throws ParseException {
		when(logMapper.getAllLog(existingUser)).thenReturn(setLogList());
		List<LogEntity> logList = logService.getAllLog(existingUser);
		assertEquals(1, logList.size(), "Error: The result is expected 1, but actual is not");
		 assertEquals(setLogList(), logList);
		verify(logMapper, times(1)).getAllLog(existingUser);
	}

	// Check create() in LogMapper return id and is called once
	@Test
	void checkAddNewLogAndExist() {
		LogForm log = usersLogInfo();
		when(logMapper.create(log)).thenReturn(5);
		int result = logService.create(log);
		assertEquals(result, log.getId(), "Error: The result is expected same value as newLog's id, but actual is not");
		verify(logMapper, times(1)).create(log);
	}

	// Check delete() in LogMapper return true and is called once
	@Test
	void checkDeleteLogAndReturnTrue() {
		LogForm log = usersLogInfo();
		when(logMapper.delete(log)).thenReturn(true);
		boolean result = logService.delete(log);
		assertTrue(result, "Error: The boolean expected true, but result of test is false");
		verify(logMapper, times(1)).delete(log);
	}

	// Check edit() in LogMapper return true and is called once
	@Test
	void checkEditLogAndReturnTrue() {
		LogForm log = usersLogInfo();
		when(logMapper.edit(log)).thenReturn(true);
		boolean result = logService.edit(log);
		assertTrue(result, "Error: The boolean expected true, but result of test is false");
		verify(logMapper, times(1)).edit(log);
	}
	
	public List<LogEntity> setLogList() throws ParseException {
		LogEntity logEntity = new LogEntity();
		logEntity.setMood(2);
		logEntity.setSelectedOne(1);
		logEntity.setSelectedTwo(1);
		logEntity.setSelectedThree(1);
		logEntity.setMemo("This is memo for test");
		logEntity.setId(1);
		logEntity.setUserUid(existingUser);
		logEntity.setDeleteFlg(0);
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.US);
		logEntity.setDate(dateFormat.parse("Thu Oct 06 02:00:00 CEST 2022"));
		List<LogEntity> logList = new ArrayList<LogEntity>(List.of(logEntity));
		return logList;
	}

	public LogForm usersLogInfo() {
		LogForm newLog = new LogForm();
		newLog.setDate(new Date());
		newLog.setMood(3);
		newLog.setSelectedOne(1);
		newLog.setSelectedTwo(0);
		newLog.setSelectedThree(0);
		newLog.setUserUid(existingUser);
		newLog.setDeleteFlg(0);
		newLog.setId(5);
		return newLog;
	}

}
