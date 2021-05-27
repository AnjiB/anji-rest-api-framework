package com.anji.mendix.api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error POJO
 * 
 * @author boddupally.anji
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorJson {

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("code")
	private String code;
	
	@JsonProperty("status")
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
