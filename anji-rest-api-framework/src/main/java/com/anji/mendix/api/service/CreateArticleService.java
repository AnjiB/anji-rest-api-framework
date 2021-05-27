package com.anji.mendix.api.service;

import static com.anji.mendix.api.constants.EndPoint.ARTICLES;
import com.anji.framework.api.builder.RequestBuilder;
import com.anji.framework.api.impl.ApiResponse;
import com.anji.framework.api.impl.PostApiImpl;
import com.anji.mendix.api.pojo.ArticleRequestAndResponse;

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
