package com.rabbit.tracker.entity;

import java.util.Date;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table(value="log_info")
public class LogEntity {
	private int mood;
	private int selectedOne;
	private int selectedTwo;
	private int selectedThree;
	private String memo;
	private Date date;
	private int id;
	private String userUid;
	private int deleteFlg; 
	

}
