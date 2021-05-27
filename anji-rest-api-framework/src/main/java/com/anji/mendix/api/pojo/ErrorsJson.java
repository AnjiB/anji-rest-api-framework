package com.anji.mendix.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Errors POJO
 * 
 * @author boddupally.anji
 */

public class ErrorsJson {

	@JsonProperty("username")
	private String username;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("error")
	private ErrorJson error;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ErrorJson getError() {
		return error;
	}

	public void setError(ErrorJson error) {
		this.error = error;
	}
}
