package com.anji.rest.api.service;

import static com.anji.rest.api.constants.EndPoint.ARTICLES;

import com.anji.framework.api.builder.RequestBuilder;
import com.anji.framework.api.impl.ApiResponse;
import com.anji.framework.api.impl.PostApiImpl;
import com.anji.framework.commons.config.ConfigLoader;
import com.anji.rest.api.pojo.ArticleRequestAndResponse;
import com.anji.rest.api.pojo.User;

/**
 * 
 * Service for create article api
 * 
 * @author boddupally.anji
 */
public class CreateArticleService {
	
	private CreateArticleService () {
		
	}
	
	public static CreateArticleService getInstance() {
		return new CreateArticleService();
	}
	
	public ApiResponse<ArticleRequestAndResponse> createArticle(String username, String password, ArticleRequestAndResponse ar, boolean authRequired) throws Exception {
		
		RequestBuilder builder = new RequestBuilder.Builder()
				.withUsername(username)
				.withPassword(password)
				.withAuthRequired(authRequired)
				.withReqUrl(ARTICLES).withRequestObject(ar).build();
		return new PostApiImpl<>(ArticleRequestAndResponse.class).post(builder);
	}
	
	
	public ApiResponse<ArticleRequestAndResponse> createArticle(String username, ArticleRequestAndResponse createArticleRequest) throws Exception {
		return createArticle(username, ConfigLoader.getDefaultPassword(), createArticleRequest, true);
	}
	
	public ApiResponse<ArticleRequestAndResponse> createArticle(String username, ArticleRequestAndResponse createArticleRequest, boolean authRequired) throws Exception {
		return createArticle(username, ConfigLoader.getDefaultPassword(), createArticleRequest, authRequired);
	}
	
	public ApiResponse<ArticleRequestAndResponse> createArticle(User user, ArticleRequestAndResponse createArticleRequest) throws Exception {
		return createArticle(user.getEmail(), user.getPassword(), createArticleRequest, true);
	}
}
