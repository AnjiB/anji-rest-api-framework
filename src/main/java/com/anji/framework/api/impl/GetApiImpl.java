package com.anji.framework.api.impl;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.client.methods.HttpGet;

import com.anji.framework.api.builder.RequestBuilder;

/**
 * 
 * Implementation of GET Request

 * @author boddupally.anji
 *
 * @param <T>
 */
public class GetApiImpl<T> extends AbstractApiImpl<T> {
	
	public GetApiImpl(Class<T> klass) {
		super(klass);
	}
	
	public ApiResponse<T> get(RequestBuilder builder) throws Exception {
		StopWatch sw = new StopWatch();
		sw.start();
		Client client = ClientService.getClient(builder);
		ApiOutput output = client.getOrDelete(builder, new HttpGet());
		sw.stop();
		
		return getResponse(output, sw.getTime());
	}
}
