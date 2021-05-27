package com.anji.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.anji.framework.api.builder.RequestBuilder;
import com.anji.framework.api.impl.ApiResponse;
import com.anji.framework.api.impl.PostApiImpl;
import com.anji.mendix.api.data.TestDataFactory;
import com.anji.mendix.api.enus.Filter;
import com.anji.mendix.api.pojo.ArticleRequestAndResponse;
import com.anji.mendix.api.pojo.ArticlesResponse;
import com.anji.mendix.api.pojo.Request;
import com.anji.mendix.api.service.CreateArticleService;
import com.anji.mendix.api.service.GetArticlesService;
import com.anji.mendix.api.service.LoginService;
import com.anji.mendix.api.service.RegisterService;
import com.google.common.collect.Maps;

public class CreateArticleTest {

	public static final String ARTICLES = "/api/articles";
	
	private Request userRequest;

	private CreateArticleService creatArticle = CreateArticleService.getInstance();

	private GetArticlesService getArticleService = GetArticlesService.getInstance();
	
	private LoginService loginService = LoginService.getInstance();

	@BeforeTest
	public void registerUser() throws Exception {

		Request registerUserRequest = TestDataFactory.getValidUserRequest();
		RegisterService registerService = RegisterService.getInstance();
		registerService.registerUser(registerUserRequest);

	}

	/*
	 * Testing Create Article flow..
	 */
	@Test
	public void testCreateArticle() throws Exception {

		ArticleRequestAndResponse requestObject = TestDataFactory.getValidArticle();

		ApiResponse<ArticleRequestAndResponse> response = creatArticle.createArticle(userRequest.getUser().getEmail(), requestObject);

		assertThat(response.getResponseCode()).isEqualTo(HttpStatus.SC_OK);
		ArticleRequestAndResponse aResponse = response.getResponse();

		aResponse.articleAssertThat().thereIsNoError().articleBodyIs(requestObject.getArticle().getBody())
				.articleDescriptionIs(requestObject.getArticle().getDescription())
				.articleTitleIs(requestObject.getArticle().getTitle())
				.articleTagListIs(requestObject.getArticle().getTagList()).authorIs(userRequest.getUser().getUsername());

		// check if article is available in the system.
		Map<String, String> queryParam = Maps.newHashMap();
		queryParam.put(Filter.author.name(), userRequest.getUser().getUsername());

		ApiResponse<ArticlesResponse> articlesResponse = getArticleService.getArticles(queryParam);
		assertThat(response.getResponseCode()).isEqualTo(HttpStatus.SC_OK);
		articlesResponse.getResponse().assertThat().articleCountIs(1);

	}

	/*
	 * Testing Create Article flow with special characters
	 */
	//@Test
	public void testCreateArticleWithSpecialCharacters() throws Exception {
		
		ArticleRequestAndResponse requestObject = TestDataFactory.getValidArticle();

		ApiResponse<ArticleRequestAndResponse> response = creatArticle.createArticle(userRequest.getUser().getEmail(), requestObject);

		assertThat(response.getResponseCode()).isEqualTo(HttpStatus.SC_OK);
		ArticleRequestAndResponse aResponse = response.getResponse();

		aResponse.articleAssertThat().thereIsNoError().articleBodyIs(requestObject.getArticle().getBody())
				.articleDescriptionIs(requestObject.getArticle().getDescription())
				.articleTitleIs(requestObject.getArticle().getTitle())
				.articleTagListIs(requestObject.getArticle().getTagList()).authorIs(userRequest.getUser().getUsername());

		// check if article is available in the system.
		Map<String, String> queryParam = Maps.newHashMap();
		queryParam.put(Filter.author.name(), userRequest.getUser().getUsername());

		ApiResponse<ArticlesResponse> articlesResponse = getArticleService.getArticles(queryParam);
		assertThat(response.getResponseCode()).isEqualTo(HttpStatus.SC_OK);
		articlesResponse.getResponse().assertThat().articleCountIs(1);

	}

	/*
	 * Testing create article flow without authorization
	 */
	//@Test
	public void testArticleCannotBeCreatedWithoutAuthorization() throws Exception {

		ArticleRequestAndResponse requestObject = TestDataFactory.getValidArticle();
		ApiResponse<ArticleRequestAndResponse> response = creatArticle.createArticle(userRequest.getUser().getEmail(), requestObject, false);
		assertThat(response.getResponseCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
		response.getResponse().articleAssertThat()
				.thereIsAnError()
				.errorAssert()
				.errorMessageIs("No authorization token was found")
				.errorStatusIs(HttpStatus.SC_UNAUTHORIZED);

	}

	/*
	 * Testing create article flow with previous token
	 */

	//@Test
	public void testCreateArticleWithPreviousAuthToken() throws Exception {
		
		ArticleRequestAndResponse requestObject = TestDataFactory.getValidArticle();
		
		RequestBuilder builder = new RequestBuilder.Builder()
				.withUsername(userRequest.getUser().getEmail())
				.withAuthRequired(true)
				.withReqUrl(ARTICLES)
				.withRequestObject(requestObject)
				.withCachedClient()
				.build();
		ApiResponse<ArticleRequestAndResponse> response = new PostApiImpl<>(ArticleRequestAndResponse.class).post(builder);

		assertThat(response.getResponseCode()).isEqualTo(HttpStatus.SC_OK);
		ArticleRequestAndResponse aResponse = response.getResponse();

		aResponse.articleAssertThat().thereIsNoError().articleBodyIs(requestObject.getArticle().getBody())
				.articleDescriptionIs(requestObject.getArticle().getDescription())
				.articleTitleIs(requestObject.getArticle().getTitle())
				.articleTagListIs(requestObject.getArticle().getTagList()).authorIs(userRequest.getUser().getEmail());

		// login again to get new token
		loginService.login(userRequest);
		
		// use same token
		requestObject = TestDataFactory.getValidArticle();
		builder = new RequestBuilder.Builder()
				.withUsername(userRequest.getUser().getEmail())
				.withAuthRequired(true)
				.withReqUrl(ARTICLES)
				.withRequestObject(requestObject)
				.withCachedClient()
				.build();
		response = new PostApiImpl<>(ArticleRequestAndResponse.class).post(builder);
		
		assertThat(response.getResponseCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);

	}

}
