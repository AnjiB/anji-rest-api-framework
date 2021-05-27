package com.anji.framework.api.impl;



import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.client.methods.HttpPost;

import com.anji.framework.api.builder.RequestBuilder;


/**
 * Implementation of POST Request
 * 
 * @author boddupally.anji
 *
 * @param <T>
 */
public class PostApiImpl<T> extends AbstractApiImpl<T> {

	public PostApiImpl(Class<T> klass) {
		super(klass);
	}
	
	public ApiResponse<T> post(RequestBuilder builder) throws Exception {
		StopWatch sw = new StopWatch();
		sw.start();
		Client client = ClientService.getClient(builder);
		ApiOutput output = client.postOrPatchOrPut(builder, new HttpPost());
		sw.stop();
		
		return getResponse(output, sw.getTime());
	}
}
