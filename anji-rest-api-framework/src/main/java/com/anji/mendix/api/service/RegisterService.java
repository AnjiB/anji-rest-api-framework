package com.anji.mendix.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static com.anji.mendix.api.constants.EndPoint.REGISTER;
import org.apache.http.HttpStatus;

import com.anji.framework.api.builder.RequestBuilder;
import com.anji.framework.api.impl.ApiResponse;
import com.anji.framework.api.impl.PostApiImpl;
import com.anji.mendix.api.pojo.Request;
import com.anji.mendix.api.pojo.Response;
import com.anji.mendix.api.pojo.User;

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

		assertThat(response.getResponseCode()).isEqualTo(HttpStatus.SC_OK);

		return response.getResponse().getUser();
	}
}
