package com.anji.framework.api.builder;


import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.anji.framework.api.enums.ApiContentType;
import com.anji.framework.api.enums.ApiHeaders;


/**
 * 
 * Builder to get request details from test
 * 
 * @author boddupally.anji
 */

public class RequestBuilder {

	private int waitTime;
	
	private String username;
	
	private String password;
	
	private String requestBody;
	
	private Object requestObject;
	
	private String requestUrl;
	
	private String baseUrl;
	
	private Map<String, String> queryParameters;
	
	private Map<String, String> cookies;
	
	private Map<ApiHeaders, String> reqHeaders;
	
	private ApiContentType contentType;
	
	private URL completeRequestUrl;
	
	private List<String> pathParameters;
	
	private boolean isAuthRequired;
	
	private boolean isClientCached;
		
	/**
	 * @return the waitTime
	 */
	public int getWaitTime() {
		return waitTime;
	}

	/**
	 * @param waitTime the waitTime to set
	 */
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the requestBody
	 */
	public String getRequestBody() {
		return requestBody;
	}

	/**
	 * @param requestBody the requestBody to set
	 */
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	/**
	 * @return the requestObject
	 */
	public Object getRequestObject() {
		return requestObject;
	}

	/**
	 * @param requestObject the requestObject to set
	 */
	public void setRequestObject(Object requestObject) {
		this.requestObject = requestObject;
	}

	/**
	 * @return the requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}

	/**
	 * @param requestUrl the requestUrl to set
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	/**
	 * @return the baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * @param baseUrl the baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * @return the queryParameters
	 */
	public Map<String, String> getQueryParameters() {
		return queryParameters;
	}

	/**
	 * @param queryParameters the queryParameters to set
	 */
	public void setQueryParameters(Map<String, String> queryParameters) {
		this.queryParameters = queryParameters;
	}

	/**
	 * @return the cookies
	 */
	public Map<String, String> getCookies() {
		return cookies;
	}

	/**
	 * @param cookies the cookies to set
	 */
	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	/**
	 * @return the reqHeaders
	 */
	public Map<ApiHeaders, String> getReqHeaders() {
		return reqHeaders;
	}

	/**
	 * @param reqHeaders the reqHeaders to set
	 */
	public void setReqHeaders(Map<ApiHeaders, String> reqHeaders) {
		this.reqHeaders = reqHeaders;
	}

	/**
	 * @return the contentType
	 */
	public ApiContentType getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(ApiContentType contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the url
	 */
	public URL getCompleteReqUrl() {
		return completeRequestUrl;
	}

	/**
	 * @param url the url to set
	 */
	public void setCompleteReqUrl(URL url) {
		this.completeRequestUrl = url;
	}

	/**
	 * @return the path
	 */
	public List<String> getPathParameters() {
		return pathParameters;
	}

	/**
	 * @param path the path to set
	 */
	public void setPathParameters(List<String> pathParameters) {
		this.pathParameters = pathParameters;
	}

	/**
	 * @return the isAuthRequired
	 */
	public boolean isAuthRequired() {
		return isAuthRequired;
	}

	/**
	 * @param isAuthRequired the isAuthRequired to set
	 */
	public void setAuthRequired(boolean isAuthRequired) {
		this.isAuthRequired = isAuthRequired;
	}

	/**
	 * @return the isClientCached
	 */
	public boolean isClientCached() {
		return isClientCached;
	}
	
	/**
	 * @param isClientCached the isClientCached to set
	 */
	public void setClientCached(boolean isClientCached) {
		this.isClientCached = isClientCached;
	}




	public static class Builder {
		
		private int waitTime;
		
		private String username;
		
		private String password;
		
		private String requestBody;
		
		private Object requestObject;
		
		private String requestUrl;
		
		private String baseUrl;
		
		private Map<String, String> queryParameters;
		
		private ApiContentType contentType = ApiContentType.JSON;
		
		private List<String> pathParameters;
		
		private Map<String, String> cookeis;
		
		private Map<ApiHeaders, String> reqHeaders;
				
		private boolean isAuthRequired;
		
		private boolean isClientCached;
			
		public Builder withUsername(String username) {
			this.username = username;
			return this;
		}
		
		public Builder withPassword(String password) {
			this.password = password;
			return this;
		}
		
		public Builder withReqUrl(String reqUrl) {
			this.requestUrl = reqUrl;
			return this;
		}
		
		public Builder withRequestBody(String body) {
			requestBody = body;
			return this;
		}
		
		public Builder withQueryParams(Map<String, String> params) {
			queryParameters = params;
			return this;
		}
		
		public Builder withRequestObject(Object object) {
			requestObject = object;
			return this;
		}
		
		public Builder withApiWaitTime(int waitTime) {
			this.waitTime = waitTime;
			return this;
		}
		
		public Builder withContentType(ApiContentType contentType) {
			this.contentType = contentType;
			return this;
		}
		
		public Builder withPathParameter(String ... pathParameters) {
			if(pathParameters.length > 0) {
				this.pathParameters = Arrays.asList(pathParameters);;
			}
			return this;
		}
		
		public Builder withCookies(Map<String, String> cookMap) {
			this.cookeis = cookMap;
			return this;
		}
		
		public Builder withRequestHeaders(Map<ApiHeaders, String> reqHeaders) {
			this.reqHeaders = reqHeaders;
			return this;
		}
				
		public Builder withBaseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
			return this;
		}
		
		public Builder withAuthRequired(boolean isAuthRequired) {
			this.isAuthRequired = isAuthRequired;
			return this;
		}
		
		public Builder withCachedClient() {
			this.isClientCached = true;
			return this;
		}
				
		public RequestBuilder build() {
			RequestBuilder builder = new RequestBuilder();
			builder.setUsername(this.username);
			builder.setPassword(this.password);
			builder.setRequestUrl(this.requestUrl);
			builder.setRequestBody(this.requestBody);
			builder.setRequestObject(this.requestObject);
			builder.setQueryParameters(this.queryParameters);
			builder.setWaitTime(this.waitTime);
			builder.setContentType(this.contentType);
			builder.setPathParameters(this.pathParameters);
			builder.setCookies(this.cookeis);
			builder.setReqHeaders(this.reqHeaders);
			builder.setBaseUrl(baseUrl);
			builder.setAuthRequired(this.isAuthRequired);
			builder.setClientCached(this.isClientCached);		
			return builder;
		}
	}
}
