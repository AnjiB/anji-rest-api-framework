package com.anji.mendix.api.asserts;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.api.AbstractAssert;

import com.anji.mendix.api.pojo.ErrorsJson;

/**
 * Error Response Assertions
 * 
 * @author boddupally.anji
 *
 */
public class ErrorResponseAssert extends AbstractAssert<ErrorResponseAssert, ErrorsJson> {

	private String errorInfoMessage = "Expected is: %s but actual is %s";

	public ErrorResponseAssert(ErrorsJson actual) {
		super(actual, ErrorResponseAssert.class);
	}

	public ErrorResponseAssert errorMessageIs(String expMessage) {
		assertThat(actual.getMessage()).as(format(errorInfoMessage, expMessage, actual.getMessage()))
				.isEqualTo(expMessage);
		return this;
	}

	public ErrorResponseAssert errorStatusIs(int expStatus) {
		assertThat(actual.getError()).isNotNull();
		assertThat(actual.getError().getStatus())
				.as(format(errorInfoMessage, expStatus, actual.getError().getStatus())).isEqualTo(expStatus);
		return this;
	}
	
	public ErrorResponseAssert usernameErrorMessageIs(String expMessage) {
		assertThat(actual.getUsername()).as(format(errorInfoMessage, expMessage, actual.getUsername()))
				.isEqualTo(expMessage);
		return this;
	}
	
	public ErrorResponseAssert emailErrorMessageIs(String expMessage) {
		assertThat(actual.getEmail()).as(format(errorInfoMessage, expMessage, actual.getEmail()))
				.isEqualTo(expMessage);
		return this;
	}
	
	public ErrorResponseAssert usernameIsNull() {
		assertThat(actual.getUsername()).isNull();
		return this;
	}
	
	public ErrorResponseAssert emailIsNull() {
		assertThat(actual.getEmail()).isNull();
		return this;
	}
}
