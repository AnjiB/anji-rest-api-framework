package com.anji.framework.api.impl;


import java.util.Map;

/**
 * Response object to be used by tests for assertions
 * 
 * @author boddupally.anji
 * 
 * @param <T>
 */
public abstract class ApiResponse<T> {
	
	private ApiOutput apiOutput;
	
	private long responseTime;

	public ApiResponse(ApiOutput output, long responseTime) {
		apiOutput = output;
		this.responseTime = responseTime;
	}
	
	public abstract T getResponse() throws Exception;
	
	public long getExecTime() {
		return this.responseTime;
	}
	
	public int getResponseCode() {
		return apiOutput.getResponseCode();
	}
	
	public String getResponseAsString() {
		return apiOutput.getResponseString();
	}
	
	public Map<String, String> getResponseHeaders() {
		return apiOutput.getResponseHeaders();
	}
	
	public long getResponseTime() {
		return responseTime;
	}
}
