package com.anji.mendix.api.service;

import java.util.Map;

import com.anji.framework.api.builder.RequestBuilder;
import com.anji.framework.api.impl.ApiResponse;
import com.anji.framework.api.impl.GetApiImpl;
import com.anji.mendix.api.pojo.ArticlesResponse;

public class GetArticlesService {

	public static final String ARTICLES = "/api/articles";

	private GetArticlesService() {

	}

	public static GetArticlesService getInstance() {
		return new GetArticlesService();
	}

	public ApiResponse<ArticlesResponse> getArticles(Map<String, String> queryParam) throws Exception {

		RequestBuilder builder = new RequestBuilder.Builder().withQueryParams(queryParam).withReqUrl(ARTICLES).build();
		return new GetApiImpl<>(ArticlesResponse.class).get(builder);
	}
}
