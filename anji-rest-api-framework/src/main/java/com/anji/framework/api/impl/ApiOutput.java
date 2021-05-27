package com.anji.framework.api.impl;


import java.util.Map;

/**
 * Holds the response information from the client.
 * 
 * @author boddupally.anji
 *
 */
public class ApiOutput {

	private int responseCode;
	
	private String responseString;
	
	private Map<String, String> responseHeaders;
	
	public ApiOutput(String responseString, int responseCode, Map<String, String> responseHeaders) {
		this.responseString = responseString;
		this.responseCode = responseCode;
		this.responseHeaders = responseHeaders;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseString() {
		return responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	public Map<String, String> getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders(Map<String, String> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}
}
