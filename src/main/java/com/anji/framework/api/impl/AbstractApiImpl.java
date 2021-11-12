package com.anji.framework.api.impl;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.anji.framework.api.enums.ApiContentType;
import com.anji.framework.api.enums.ApiHeaders;
import com.anji.framework.api.utils.ConvertionUtil;


/**
 * Common implementation that handles the conversion of response string into POJO
 * 
 * @author boddupally.anji
 *
 * @param <T>
 */
abstract class AbstractApiImpl<T> {
	
	private static final Logger LOGGER  = LogManager.getLogger(AbstractApiImpl.class);

	private Class<T> klass;

	protected AbstractApiImpl(Class<T> klass) {
		this.klass = klass;
	}

	protected ApiResponse<T> getResponse(ApiOutput output, long time) {
		return new ApiResponse<T>(output, time) {

			@SuppressWarnings("unchecked")
			@Override
			public T getResponse() throws Exception {
				String resonseString = output.getResponseString();
				LOGGER.info("Response is: " + resonseString);
				if (String.class.isAssignableFrom(klass)) {
					return (T) resonseString;
				} else if (JSONObject.class.isAssignableFrom(klass)) {
					return (T) new JSONObject(klass);
				} else {
					String responseContType = output.getResponseHeaders().get(ApiHeaders.CONTENT_TYPE.getHeader());
					if (responseContType.equals(ApiContentType.JSON.getContentType())) {
						return ConvertionUtil.convertJsonStringToPojo(resonseString, klass);
					} else if (responseContType.equals(ApiContentType.XML.getContentType())) {
						return ConvertionUtil.converToXmlStringPojo(resonseString, klass);
					} else {
						return (T) resonseString;
					}
				}
			}
		};
	}
}
