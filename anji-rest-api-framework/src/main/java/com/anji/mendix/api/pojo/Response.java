package com.anji.mendix.api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response POJO
 * 
 * @author boddupally.anji
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

	@JsonProperty("user")
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
