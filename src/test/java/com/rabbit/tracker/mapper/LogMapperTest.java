package com.rabbit.tracker.mapper;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.rabbit.tracker.entity.LogEntity;
import com.rabbit.tracker.entity.UserEntity;
import com.rabbit.tracker.form.LogForm;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@MybatisTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LogMapperTest {

	@Autowired
	LogMapper lm;
	@Autowired
	LogMapperTestForSQL tfs;

	private String existingUser = "sample1234test";
	private String nonExistingUser = "sample0000test";
	private String newUser = "sample9999test";

	// Check to return true for a UID that already exists in DB
	@Test
	void checkExistingUserReturnTrue() {
		boolean result = lm.checkLoginUser(existingUser);
		assertTrue(result, "Error: The boolean expected true, but result of test is false");
	}

	// Check to exist the user in DB which return true
	@Test
	void checkLoginUserExists() {
		UserEntity result = tfs.checkExistLoginUser(existingUser);
		assertEquals(result.getId(), 93, "Error: The result expected 1, but actual is" + result.getId());
		assertEquals(result.getUserUid(), existingUser,
				"Error: The result expected 'sample1234test', but actual is not");
//		assertThat(result).containsExactly(1, "theUserToken");
	}

	// Check to return false for a UID that already does not exist in DB
	@Test
	void checkNonExistingUserReturnFalse() {
		boolean result = lm.checkLoginUser(nonExistingUser);
		assertFalse(result, "Error: The boolean expected false, but result of test is true");
	}

	// Check not to exist the user in DB which return false
	@Test
	void checkLoginUserDoesNotExists() {
		UserEntity result = tfs.checkExistLoginUser(nonExistingUser);
		assertNull(result, "Error: The result of Id expected Null, but actual is not");
	}

	// Check to add new UID and exist in DB
	@Test
	void checkAddLoginUserAndExistInDB() {
		lm.addLoginUser(newUser);
		UserEntity result = tfs.checkExistLoginUser(newUser);
		assertEquals(result.getUserUid(), newUser, "Error: The result expected in DB, but result does not exist");
	}

	// Check to return exception when UID which already exist is inserted in DB
	@Test
	void tryAddLoginUserAndExpectedExceptionFail() {
		assertThrows(DuplicateKeyException.class, () -> {
			lm.addLoginUser(existingUser);
		}, "Error: SQLException was expected");
	}

	// Get 1 list which already inserted in DB by UID and Check the the contents of
	// the list
	@Test
	void get1listByUIDAndcheckTheData() throws ParseException {
		List<LogEntity> logList = lm.getAllLog(existingUser);
		assertThat(logList).hasSize(1).containsExactly(setExistingDataInDB());
	}

	// Set data which exist in DB into LogEntity
	public LogEntity setExistingDataInDB() throws ParseException {
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
		return logEntity;
	}

	// Check not to get list which does not exist in DB by UID
	@Test
	void checkDoesNotGetLogsByUID() {
		List<LogEntity> logList = lm.getAllLog(nonExistingUser);
		assertEquals(logList.size(), 0,
				"Error: The result expected not to get any list by this UID, but actual it got some list");
	}

	// Check to insert new log and to exist the log in DB
	@Test
	public void checkAddNewLogAndExistInDB() {
		LogForm newLog = new LogForm();
		newLog.setDate(new Date());
		newLog.setMood(3);
		newLog.setSelectedOne(1);
		newLog.setSelectedTwo(0);
		newLog.setSelectedThree(0);
		newLog.setUserUid(existingUser);
		newLog.setDeleteFlg(0);
		lm.create(newLog);
		LogEntity theLog = tfs.getLogByIdAndUid(newLog);
		
		assertThat(theLog.getDate()).isEqualTo(newLog.getDate());
		assertThat(theLog.getMood()).isEqualTo(newLog.getMood());
		assertThat(theLog.getSelectedOne()).isEqualTo(newLog.getSelectedOne());
		assertThat(theLog.getSelectedTwo()).isEqualTo(newLog.getSelectedTwo());
		assertThat(theLog.getSelectedThree()).isEqualTo(newLog.getSelectedThree());
		assertThat(theLog.getDeleteFlg()).isEqualTo(newLog.getDeleteFlg());
		assertThat(theLog.getId()).isEqualTo(newLog.getId());	
	}

	// Check to delete log by ID and UID and Check delete_flg of deletedlog is 1
	@Test
	void checkDeleteLogAndDeleteFlg() {
		LogForm log = new LogForm();
		log.setId(1);
		log.setUserUid(existingUser);
		boolean result = lm.delete(log);
		int deleteFlg = tfs.checkDeleteFlgAfterDelete(existingUser);
		
		assertTrue(result, "Error: The boolean expected true, but result of test is false");
		assertEquals(deleteFlg, 1, "Error: The result expected 1, but actual is 0");
	}

	// Check to edit log by ID and UID return true
	@Test
	void checkEditLogbyIdAndUID() {
		LogForm editLog = new LogForm();
		editLog.setMood(2);
		editLog.setSelectedOne(0);
		editLog.setSelectedTwo(1);
		editLog.setSelectedThree(1);
		editLog.setUserUid(existingUser);
		editLog.setId(1);
		editLog.setMemo("Edit this log for test");
		boolean result= lm.edit(editLog);
		
		LogEntity theLog = tfs.getLogByIdAndUid(editLog);
		assertTrue(result, "Error: The boolean expected true, but result of test is false");
		assertThat(theLog.getMood()).isEqualTo(editLog.getMood());
		assertThat(theLog.getSelectedOne()).isEqualTo(editLog.getSelectedOne());
		assertThat(theLog.getSelectedTwo()).isEqualTo(editLog.getSelectedTwo());
		assertThat(theLog.getSelectedThree()).isEqualTo(editLog.getSelectedThree());
		assertThat(theLog.getMemo()).isEqualTo(editLog.getMemo());
	}
}