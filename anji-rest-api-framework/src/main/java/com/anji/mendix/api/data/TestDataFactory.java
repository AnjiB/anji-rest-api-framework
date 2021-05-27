package com.anji.mendix.api.data;

import org.apache.commons.lang3.RandomStringUtils;

import com.anji.mendix.api.pojo.Article;
import com.anji.mendix.api.pojo.ArticleRequestAndResponse;
import com.anji.mendix.api.pojo.Request;
import com.anji.mendix.api.pojo.User;

public class TestDataFactory {

	public static User getValidUser() {
		return new User.Builder().withUsername("testuser" + RandomStringUtils.randomAlphanumeric(5))
				.withPassword("wachtwoord").withEmail(RandomStringUtils.randomAlphanumeric(5) + "@example.com").build();
	}

	public static Request getValidUserRequest() {
		Request req = new Request();
		User user = getValidUser();
		req.setUser(user);
		return req;
	}
	
	public static ArticleRequestAndResponse getValidArticle() {
		ArticleRequestAndResponse ar = new ArticleRequestAndResponse();	
		Article article = new Article.Builder()
				.withArticleTitle(RandomStringUtils.randomAlphanumeric(50))
				.withArticleBody(RandomStringUtils.randomAlphanumeric(50))
				.withArticleDescription(RandomStringUtils.randomAlphanumeric(50))
				.withArticleTags(RandomStringUtils.randomAlphanumeric(5))
				.build();
		ar.setArticle(article);
		return ar;
	}
	
	public static ArticleRequestAndResponse getValidArticleWithSpecialCharacters() {
		ArticleRequestAndResponse ar = new ArticleRequestAndResponse();	
		Article article = new Article.Builder()
				.withArticleTitle(RandomStringUtils.randomAlphanumeric(50))
				.withArticleBody(RandomStringUtils.randomAlphanumeric(50))
				.withArticleDescription(RandomStringUtils.randomAlphanumeric(50))
				.withArticleTags("<script>alert();</script>", "!@#$%\\0[]&*()+_()")
				.build();
		
		ar.setArticle(article);
		return ar;
	}
}
