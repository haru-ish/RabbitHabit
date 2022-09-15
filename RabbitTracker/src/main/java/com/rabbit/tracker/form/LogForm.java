package com.rabbit.tracker.form;

import lombok.Data;

import java.util.Date;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
public class LogForm {
	@Range(min=1, max=5 )
	private int mood;
	@Range(min=0, max=1 )
	private int selectedOne;
	@Range(min=0, max=1 )
	private int selectedTwo;
	@Range(min=0, max=1 )
	private int selectedThree;
	@Length(max= 100 )
	private String memo;
	private Date date;
	private int id;
	private int userId;
	private String userUid;
	private int deleteFlg; 

}

