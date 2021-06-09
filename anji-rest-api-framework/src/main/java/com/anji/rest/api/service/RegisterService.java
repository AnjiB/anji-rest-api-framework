package com.anji.rest.api.service;

import static com.anji.rest.api.constants.EndPoint.REGISTER;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;

import com.anji.framework.api.builder.RequestBuilder;
import com.anji.framework.api.impl.ApiResponse;
import com.anji.framework.api.impl.PostApiImpl;
import com.anji.rest.api.pojo.Request;
import com.anji.rest.api.pojo.Response;
import com.anji.rest.api.pojo.User;

/**
 * 
 * Service for register api
 * 
 * @author boddupally.anji
 */
public class RegisterService {

	private RegisterService() {

	}

	public static RegisterService getInstance() {
		return new RegisterService();
	}

	public User registerUser(Request request) throws Exception {

		RequestBuilder builder = new RequestBuilder.Builder()
				.withReqUrl(REGISTER)
				.withRequestObject(request)
				.build();
		ApiResponse<Response> response = new PostApiImpl<>(Response.class).post(builder);

		assertThat(response.getResponseCode()).isEqualTo(HttpStatus.SC_CREATED);

		return response.getResponse().getUser();
	}
}
