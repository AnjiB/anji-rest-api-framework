package com.anji.mendix.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static com.anji.mendix.api.constants.EndPoint.LOGIN;
import org.apache.http.HttpStatus;

import com.anji.framework.api.builder.RequestBuilder;
import com.anji.framework.api.impl.ApiResponse;
import com.anji.framework.api.impl.PostApiImpl;
import com.anji.mendix.api.pojo.Request;
import com.anji.mendix.api.pojo.Response;

/**
 * 
 * Service for login api
 * 
 * @author boddupally.anji
 */
public class LoginService {

	private LoginService() {

	}

	public static LoginService getInstance() {
		return new LoginService();
	}

	public void login(Request request) throws Exception {

		RequestBuilder builder = new RequestBuilder.Builder().withReqUrl(LOGIN).withRequestObject(request).build();

		ApiResponse<Response> response = new PostApiImpl<>(Response.class).post(builder);

		assertThat(response.getResponseCode()).isEqualTo(HttpStatus.SC_OK);
	}
}
