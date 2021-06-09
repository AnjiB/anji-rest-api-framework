package com.anji.rest.api.service;

import static com.anji.rest.api.constants.EndPoint.ARTICLES;

import java.util.Map;

import com.anji.framework.api.builder.RequestBuilder;
import com.anji.framework.api.impl.ApiResponse;
import com.anji.framework.api.impl.GetApiImpl;
import com.anji.rest.api.pojo.ArticlesResponse;

/**
 * 
 * Service for get articles api
 * 
 * @author boddupally.anji
 */
public class GetArticlesService {

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
