package com.rabbit.tracker.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.rabbit.tracker.entity.LogEntity;
import com.rabbit.tracker.entity.UserEntity;
import com.rabbit.tracker.form.LogForm;

@Mapper
public interface LogMapperTestForSQL {
	
	// Check UID which is inserted exists
	UserEntity checkExistLoginUser(String userUid);
	
	// Check deleteFlg after delete method run
	int checkDeleteFlgAfterDelete(String userUid);
	
	// Get log by Id and Uid
	LogEntity getLogByIdAndUid(LogForm form);

}
