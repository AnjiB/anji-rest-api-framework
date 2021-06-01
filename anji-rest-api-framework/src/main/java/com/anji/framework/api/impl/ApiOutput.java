package com.anji.framework.api.impl;


import java.util.Map;

/**
 * Holds the response information from the client.
 * 
 * @author boddupally.anji
 *
 */
class ApiOutput {

	private int responseCode;
	
	private String responseString;
	
	private Map<String, String> responseHeaders;
	
	ApiOutput(String responseString, int responseCode, Map<String, String> responseHeaders) {
		this.responseString = responseString;
		this.responseCode = responseCode;
		this.responseHeaders = responseHeaders;
	}

	int getResponseCode() {
		return responseCode;
	}

	void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	String getResponseString() {
		return responseString;
	}

	void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	Map<String, String> getResponseHeaders() {
		return responseHeaders;
	}

	void setResponseHeaders(Map<String, String> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}
}
