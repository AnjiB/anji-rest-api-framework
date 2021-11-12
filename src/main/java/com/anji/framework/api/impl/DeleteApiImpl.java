package com.anji.framework.api.impl;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.client.methods.HttpDelete;

import com.anji.framework.api.builder.RequestBuilder;


/**
 * Implementation of DELETE Request
 * 
 * @author boddupally.anji
 *
 * @param <T>
 */
public class DeleteApiImpl<T> extends AbstractApiImpl<T> {

	public DeleteApiImpl(Class<T> klass) {
		super(klass);
	}
	
	public ApiResponse<T> delete(RequestBuilder builder) throws Exception {
		StopWatch sw = new StopWatch();
		sw.start();
		Client client = ClientService.getClient(builder);
		ApiOutput output = client.getOrDelete(builder, new HttpDelete());
		sw.stop();
		
		return getResponse(output, sw.getTime());
	}
}
