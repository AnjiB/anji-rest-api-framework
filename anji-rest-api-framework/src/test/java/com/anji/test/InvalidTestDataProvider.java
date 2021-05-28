package com.anji.test;

import org.testng.annotations.DataProvider;

import com.anji.mendix.api.data.TestDataFactory;

public class InvalidTestDataProvider {

	@DataProvider(name = "invalid-article-data-provider")
	public Object[][] invalidProductID() {
		return new Object[][] { 
				{ TestDataFactory.getArticleObjectWithEmptyValues() }};
	}
}
