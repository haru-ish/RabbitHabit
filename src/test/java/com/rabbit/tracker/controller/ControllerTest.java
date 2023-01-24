package com.rabbit.tracker.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.rabbit.tracker.service.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.*;

import com.rabbit.tracker.entity.LogEntity;
import com.rabbit.tracker.form.LogForm;


@WebMvcTest(Controller.class)
public class ControllerTest {

	// Handle http request/response without deploying to Tomcat server
	@Autowired
	private MockMvc mvc;
	// Create mock of LogService using Mockito
	@MockBean
	LogService logService;

	// Check checkLoginUser() in LogService
	@Nested
	class checkLoginUser {

		final MockHttpServletRequestBuilder request = get("/api/checkLoginUser").accept(MediaType.APPLICATION_JSON);

		@BeforeEach
		void setUp() {
			when(logService.checkLoginUser(any(String.class))).thenReturn(true);
		}

		@Test
		@WithMockUser
		void authenticatedUserIsOK() throws Exception {
			mvc.perform(request).andExpect(status().isOk()).andExpect(content().string("true"));
		}

		@Test
		void noAuthenticationIsNG() throws Exception {
			mvc.perform(request).andExpect(status().isUnauthorized());
		}
	}

	// Check create() in LogService
	@Nested
	class create {

		final MockHttpServletRequestBuilder request = post("/api/create").contentType(MediaType.MULTIPART_FORM_DATA)
				.params(validData()).with(csrf());

		@BeforeEach
		void setUp() {
			doAnswer(invocation -> {
				LogForm logForm = invocation.getArgument(0);
				logForm.setId(5);
				return null;
			}).when(logService).create(any(LogForm.class));
		}

		// with valid data
		@Test
		@WithMockUser
		void authenticatedUserIsOK() throws Exception {
			mvc.perform(request).andExpect(status().isOk()).andExpect(content().string("5"));
		}

		// with valid data
		@Test
		void noAuthenticationIsNG() throws Exception {
			mvc.perform(request).andExpect(status().isUnauthorized());
		}

		// with invalid data
		@Test
		@WithMockUser
		void throwsExceptionDueToInvalidData() throws Exception {

			final MockHttpServletRequestBuilder request = post("/api/create").contentType(MediaType.MULTIPART_FORM_DATA)
					.params(invalidData()).with(csrf());
			mvc.perform(request).andExpect(status().isOk()).andExpect(content().string("1"));
		}
	}

	// Check edit() in LogService
	@Nested
	class edit {

		final MockHttpServletRequestBuilder request = post("/api/edit").contentType(MediaType.MULTIPART_FORM_DATA)
				.params(validData()).with(csrf());

		@BeforeEach
		void setUp() {
			doAnswer(invocation -> {
				return true;
			}).when(logService).edit(any(LogForm.class));

		}

		// with valid data
		@Test
		@WithMockUser
		void authenticatedUserIsOK() throws Exception {
			mvc.perform(request).andExpect(status().isOk()).andExpect(content().string("true"));
		}

		// with valid data
		@Test
		void noAuthenticationIsNG() throws Exception {
			mvc.perform(request).andExpect(status().isUnauthorized());
		}

		// with invalid data
		@Test
		@WithMockUser
		void throwsExceptionDueToInvalidData() throws Exception {

			final MockHttpServletRequestBuilder request = post("/api/edit").contentType(MediaType.MULTIPART_FORM_DATA)
					.params(invalidData()).with(csrf());
			mvc.perform(request).andExpect(status().isOk()).andExpect(content().string("false"));
		}
	}

	// Check delete() in LogService
	@Nested
	class delete {
		final MockHttpServletRequestBuilder request = post("/api/delete").contentType(MediaType.MULTIPART_FORM_DATA)
				.param("id", "3").with(csrf());

		@BeforeEach
		void setUp() {
			doAnswer(invocation -> {
				return true;
			}).when(logService).delete(any(LogForm.class));
		}

		// with valid data
		@Test
		@WithMockUser
		void authenticatedUserIsOK() throws Exception {
			mvc.perform(request).andExpect(status().isOk()).andExpect(content().string("true")).andDo(MockMvcResultHandlers.print());
		}

		// with valid data
		@Test
		void noAuthenticationIsNG() throws Exception {
			mvc.perform(request).andExpect(status().isUnauthorized());
		}
	}

	// Check getAllLog() in LogService
	@Nested
	class getAllLog {

		final MockHttpServletRequestBuilder request = get("/api/getAllLog").accept(MediaType.APPLICATION_JSON);

		@BeforeEach
		void setUp() throws ParseException {
			when(logService.getAllLog(any(String.class))).thenReturn(setLogList());
		}

		@Test
		@WithMockUser
		void authenticatedUserIsOK() throws Exception {
			mvc.perform(request).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$.length()", is(1)))
			.andExpect(jsonPath("$[0].mood", is(2)))
			.andExpect(jsonPath("$[0].selectedOne", is(1)))
			.andExpect(jsonPath("$[0].selectedTwo", is(1)))
			.andExpect(jsonPath("$[0].selectedThree", is(1)))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].userUid", is("sample1234test")))
			.andExpect(jsonPath("$[0].deleteFlg", is(0)));
		}

		@Test
		void noAuthenticationIsNG() throws Exception {
			mvc.perform(request).andExpect(status().isUnauthorized());
		}
	}

	public LinkedMultiValueMap<String, String> validData() {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("mood", "3");
		requestParams.add("selectedOne", "1");
		requestParams.add("selectedTwo", "0");
		requestParams.add("selectedThree", "0");
		requestParams.add("deleteFlg", "0");
		requestParams.add("memo", "Test for Controller class");
		requestParams.add("date", "Tue Oct 18 2022 02:00:00 GMT+0200 (中央ヨーロッパ夏時間)");
		return requestParams;
	}

	public LinkedMultiValueMap<String, String> invalidData() {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("mood", "");
		requestParams.add("selectedOne", "");
		requestParams.add("selectedTwo", "");
		requestParams.add("selectedThree", "");
		requestParams.add("deleteFlg", "");
		requestParams.add("date", "");
		return requestParams;
	}
	
	public List<LogEntity> setLogList() throws ParseException {
		LogEntity logEntity = new LogEntity();
		logEntity.setMood(2);
		logEntity.setSelectedOne(1);
		logEntity.setSelectedTwo(1);
		logEntity.setSelectedThree(1);
		logEntity.setId(1);
		logEntity.setUserUid("sample1234test");
		logEntity.setDeleteFlg(0);
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.US);
		logEntity.setDate(dateFormat.parse("Thu Oct 20 02:00:00 CEST 2022"));
		List<LogEntity> logList = new ArrayList<LogEntity>(List.of(logEntity));
		return logList;
	}

}
