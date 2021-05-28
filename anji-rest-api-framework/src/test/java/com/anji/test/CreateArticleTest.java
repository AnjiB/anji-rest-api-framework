package com.anji.test;

import static com.anji.mendix.api.constants.EndPoint.ARTICLES;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.testng.annotations.BeforeMethod;
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

	private Request userRequest;

	private CreateArticleService creatArticle = CreateArticleService.getInstance();

	private GetArticlesService getArticleService = GetArticlesService.getInstance();

	private LoginService loginService = LoginService.getInstance();

	@BeforeMethod
	public void registerUser() throws Exception {
		userRequest = TestDataFactory.getValidUserRequest();
		RegisterService registerService = RegisterService.getInstance();
		registerService.registerUser(userRequest);

	}

	@Test(dataProvider = "invalid-article-data-provider", dataProviderClass = InvalidTestDataProvider.class)
	public void createArticleWithEmptiesAndNulls(ArticleRequestAndResponse invalidArticleTestDataObject)
			throws Exception {

		ApiResponse<ArticleRequestAndResponse> response = creatArticle.createArticle(userRequest.getUser().getEmail(),
				invalidArticleTestDataObject);

		assertThat(response.getResponseCode()).isEqualTo(SC_BAD_REQUEST);

	}

	/*
	 * Testing Create Article flow..
	 */
	@Test(description = "Testing if valid user can create the article or not")
	public void testCreateArticle() throws Exception {

		ArticleRequestAndResponse requestObject = TestDataFactory.getValidArticle();

		ApiResponse<ArticleRequestAndResponse> response = creatArticle.createArticle(userRequest.getUser().getEmail(),
				requestObject);

		assertThat(response.getResponseCode()).isEqualTo(SC_OK);
		ArticleRequestAndResponse aResponse = response.getResponse();

		aResponse.articleAssertThat().thereIsNoError().articleBodyIs(requestObject.getArticle().getBody())
				.articleDescriptionIs(requestObject.getArticle().getDescription())
				.articleTitleIs(requestObject.getArticle().getTitle())
				.articleTagListIs(requestObject.getArticle().getTagList())
				.authorIs(userRequest.getUser().getUsername());

		// check if article is available in the system.
		Map<String, String> queryParam = Maps.newHashMap();
		queryParam.put(Filter.author.name(), userRequest.getUser().getUsername());

		ApiResponse<ArticlesResponse> articlesResponse = getArticleService.getArticles(queryParam);
		assertThat(response.getResponseCode()).isEqualTo(SC_OK);
		articlesResponse.getResponse().assertThat().articleCountIs(1);

	}

	/*
	 * Testing Create Article flow with special characters
	 */
	@Test(description = "Testing if valid user can create the article or not with special characters")
	public void testCreateArticleWithSpecialCharacters() throws Exception {

		ArticleRequestAndResponse requestObject = TestDataFactory.getValidArticle();

		ApiResponse<ArticleRequestAndResponse> response = creatArticle.createArticle(userRequest.getUser().getEmail(),
				requestObject);

		assertThat(response.getResponseCode()).isEqualTo(SC_OK);
		ArticleRequestAndResponse aResponse = response.getResponse();

		aResponse.articleAssertThat().thereIsNoError().articleBodyIs(requestObject.getArticle().getBody())
				.articleDescriptionIs(requestObject.getArticle().getDescription())
				.articleTitleIs(requestObject.getArticle().getTitle())
				.articleTagListIs(requestObject.getArticle().getTagList())
				.authorIs(userRequest.getUser().getUsername());

		// check if article is available in the system.
		Map<String, String> queryParam = Maps.newHashMap();
		queryParam.put(Filter.author.name(), userRequest.getUser().getUsername());

		ApiResponse<ArticlesResponse> articlesResponse = getArticleService.getArticles(queryParam);
		assertThat(response.getResponseCode()).isEqualTo(SC_OK);
		articlesResponse.getResponse().assertThat().articleCountIs(1);

	}

	/*
	 * Testing create article flow without authorization
	 */
	@Test(description = "Testing if user can create the article without token or not")
	public void testArticleCannotBeCreatedWithoutAuthorization() throws Exception {

		ArticleRequestAndResponse requestObject = TestDataFactory.getValidArticle();
		ApiResponse<ArticleRequestAndResponse> response = creatArticle.createArticle(userRequest.getUser().getEmail(),
				requestObject, false);
		assertThat(response.getResponseCode()).isEqualTo(SC_UNAUTHORIZED);
		response.getResponse().articleAssertThat().thereIsAnError().errorAssert()
				.errorMessageIs("No authorization token was found").errorStatusIs(SC_UNAUTHORIZED);

	}

	/*
	 * This test is failing and it is a security issue. Token should be invalidated
	 * as soon as user obtains new one.
	 */

	@Test(description = "Testing if user can create the article with existing token.")
	public void testCreateArticleWithPreviousAuthToken() throws Exception {

		ArticleRequestAndResponse requestObject = TestDataFactory.getValidArticle();

		RequestBuilder builder = new RequestBuilder.Builder().withUsername(userRequest.getUser().getEmail())
				.withAuthRequired(true).withReqUrl(ARTICLES).withRequestObject(requestObject).withCachedClient()
				.build();
		ApiResponse<ArticleRequestAndResponse> response = new PostApiImpl<>(ArticleRequestAndResponse.class)
				.post(builder);

		assertThat(response.getResponseCode()).isEqualTo(SC_OK);
		ArticleRequestAndResponse aResponse = response.getResponse();

		aResponse.articleAssertThat().thereIsNoError().articleBodyIs(requestObject.getArticle().getBody())
				.articleDescriptionIs(requestObject.getArticle().getDescription())
				.articleTitleIs(requestObject.getArticle().getTitle())
				.articleTagListIs(requestObject.getArticle().getTagList())
				.authorIs(userRequest.getUser().getUsername());

		// login again to get new token
		loginService.login(userRequest);

		// use same token
		requestObject = TestDataFactory.getValidArticle();
		builder = new RequestBuilder.Builder().withUsername(userRequest.getUser().getEmail()).withAuthRequired(true)
				.withReqUrl(ARTICLES).withRequestObject(requestObject).withCachedClient().build();
		response = new PostApiImpl<>(ArticleRequestAndResponse.class).post(builder);

		assertThat(response.getResponseCode()).isEqualTo(SC_UNAUTHORIZED);

	}

}
