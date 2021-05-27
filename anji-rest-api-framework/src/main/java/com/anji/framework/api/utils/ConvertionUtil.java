package com.anji.framework.api.utils;


import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * 
 * Util that handles the conversion of various strings to pojos and vice-versa
 * 
 * @author boddupally.anji
 *
 */
public final class ConvertionUtil {

	public static String convertPojoToJson(Object object) throws JsonProcessingException {
		Preconditions.checkNotNull(object);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

	public static <T> T convertJsonStringToPojo(String response, Class<T> klass) throws JsonParseException, JsonMappingException, IOException {
		if (Strings.isNullOrEmpty(response))
			return null;
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(response, klass);
	}

	public static String convertPojoToXml(Object object) throws JsonProcessingException {
		XmlMapper xmlMapper = new XmlMapper();
		return xmlMapper.writeValueAsString(object);
	}

	public static <T> T converToXmlStringPojo(String response, Class<T> klass) {
		if (Strings.isNullOrEmpty(response))
			return null;
		XmlMapper mapper = new XmlMapper();
		try {
			return (T) mapper.readValue(response.getBytes(), klass);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static <T> List<T> convertStringToPojoList(String jsonString, Class<T> klass) {
		if(Strings.isNullOrEmpty(jsonString))
			return null;
		try {
			CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class,
					klass);
			return new ObjectMapper().readValue(jsonString, typeReference);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
