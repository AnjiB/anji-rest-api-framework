package com.anji.mendix.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;

import com.anji.framework.api.builder.RequestBuilder;
import com.anji.framework.api.impl.ApiResponse;
import com.anji.framework.api.impl.PostApiImpl;
import com.anji.mendix.api.pojo.Request;
import com.anji.mendix.api.pojo.Response;
import com.anji.mendix.api.pojo.User;

public class RegisterService {

	public static final String REGISTER = "/api/users";

	private RegisterService() {

	}

	public static RegisterService getInstance() {
		return new RegisterService();
	}

	public User registerUser(Request request) throws Exception {

		RequestBuilder builder = new RequestBuilder.Builder()
				.withUsername("candidatex")
				.withPassword("qa-is-cool")
				.withReqUrl(REGISTER)
				.withRequestObject(request)
				.build();
		ApiResponse<Response> response = new PostApiImpl<>(Response.class).post(builder);

		assertThat(response.getResponseCode()).isEqualTo(HttpStatus.SC_OK);

		return response.getResponse().getUser();
	}
}
