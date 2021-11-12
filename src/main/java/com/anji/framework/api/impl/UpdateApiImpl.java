package com.anji.framework.api.impl;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.client.methods.HttpPut;

import com.anji.framework.api.builder.RequestBuilder;

/**
 * Implementation of PUT Request
 * 
 * @author boddupally.anji
 *
 * @param <T>
 */
public class UpdateApiImpl<T> extends AbstractApiImpl<T> {

	public UpdateApiImpl(Class<T> klass) {
		super(klass);
	}
	
	public ApiResponse<T> update(RequestBuilder builder) throws Exception {
		StopWatch sw = new StopWatch();
		sw.start();
		Client client = ClientService.getClient(builder);
		ApiOutput output = client.postOrPatchOrPut(builder, new HttpPut());
		sw.stop();
		
		return getResponse(output, sw.getTime());
	}
}
