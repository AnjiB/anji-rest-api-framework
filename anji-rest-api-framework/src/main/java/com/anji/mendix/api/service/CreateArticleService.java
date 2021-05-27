package com.anji.mendix.api.service;


import com.anji.framework.api.builder.RequestBuilder;
import com.anji.framework.api.impl.ApiResponse;
import com.anji.framework.api.impl.PostApiImpl;
import com.anji.mendix.api.pojo.ArticleRequestAndResponse;

public class CreateArticleService {

	public static final String ARTICLES = "/api/articles";
	
	private CreateArticleService () {
		
	}
	
	public static CreateArticleService getInstance() {
		return new CreateArticleService();
	}
	
	public ApiResponse<ArticleRequestAndResponse> createArticle(String username, ArticleRequestAndResponse ar, boolean authRequired) throws Exception {
		
		RequestBuilder builder = new RequestBuilder.Builder()
				.withUsername(username)
				.withAuthRequired(authRequired)
				.withReqUrl(ARTICLES).withRequestObject(ar).build();
		return new PostApiImpl<>(ArticleRequestAndResponse.class).post(builder);
	}
	
	
	public ApiResponse<ArticleRequestAndResponse> createArticle(String username, ArticleRequestAndResponse ar) throws Exception {
		return createArticle(username, ar, true);
	}
}
