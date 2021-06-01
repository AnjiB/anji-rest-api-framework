package com.anji.framework.api.impl;

import static com.anji.framework.api.enums.ApiContentType.JSON;
import static com.anji.framework.api.enums.ApiContentType.XML;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.anji.framework.api.builder.RequestBuilder;
import com.anji.framework.api.enums.ApiHeaders;
import com.anji.framework.api.utils.ConvertionUtil;
import com.anji.framework.commons.config.ConfigLoader;
import com.anji.mendix.api.constants.EndPoint;
import com.anji.mendix.api.pojo.Request;
import com.anji.mendix.api.pojo.Response;
import com.anji.mendix.api.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * 
 * Implementation of Client, that mimics the browser in real world
 * 
 * @author boddupally.anji
 *
 */
class Client {

	private static final Logger LOGGER = Logger.getLogger(Client.class);

	private int defaultWaitTime = 60;

	private CloseableHttpClient client;

	private HttpClientContext clientContext;

	// In general, tokens should not be exposed
	private String authToken;

	private String baseURL;
	
	Client(String username, String password, int defaultWaitTime, boolean loginRequired) throws Exception {
		this.defaultWaitTime = defaultWaitTime;
		this.baseURL = ConfigLoader.getBaseUrl();
		this.client = getClient();

		if (loginRequired)
			login(username, password);
	}

	Client(String username, String password, boolean loginRequired) throws Exception {
		this(username, password, 120, loginRequired);
	}

	private void login(String username, String password) throws Exception {

		Request request = new Request();
		User user = new User();
		// username is actually email
		user.setEmail(username);
		user.setPassword(password);
		request.setUser(user);
		RequestBuilder builder = new RequestBuilder.Builder().withReqUrl(EndPoint.LOGIN).withRequestObject(request)
				.build();

		HttpPost post = new HttpPost();

		ApiOutput output = postOrPatchOrPut(builder, post);

		if (output.getResponseCode() != HttpStatus.SC_OK)
			throw new HttpException("Login is not success");
		else {
			LOGGER.info("Login Response: " + output.getResponseString());
		}

		Response response = ConvertionUtil.convertJsonStringToPojo(output.getResponseString(), Response.class);

		authToken = response.getUser().getToken();
	}

	ApiOutput postOrPatchOrPut(RequestBuilder builder, HttpEntityEnclosingRequestBase postOrPatchOrPut)
			throws IOException, URISyntaxException {

		CloseableHttpResponse response = null;

		// set body

		String body = getBodyAsString(builder);
		LOGGER.info("Request Body: " + body);
		HttpEntity entity = new ByteArrayEntity(body.getBytes(UTF_8));
		postOrPatchOrPut.setEntity(entity);
		postOrPatchOrPut.setHeader(ApiHeaders.CONTENT_TYPE.getHeader(), builder.getContentType().getContentType());

		try {
			response = executeRequest(builder, postOrPatchOrPut);
			return transformResponse(response);
		} finally {
			postOrPatchOrPut.releaseConnection();
			if (response != null)
				response.close();
		}
	}

	ApiOutput getOrDelete(RequestBuilder builder, HttpRequestBase getOrDelete) throws IOException, URISyntaxException {

		CloseableHttpResponse response = null;
		try {
			response = executeRequest(builder, getOrDelete);
			return transformResponse(response);

		} finally {
			getOrDelete.releaseConnection();
			if (response != null)
				response.close();
		}

	}

	private CloseableHttpClient getClient() {
		RequestConfig config = RequestConfig.custom().setSocketTimeout(defaultWaitTime * 60)
				.setConnectionRequestTimeout(defaultWaitTime * 60).setConnectTimeout(defaultWaitTime * 60).build();

		PoolingHttpClientConnectionManager poolingManager = new PoolingHttpClientConnectionManager();
		poolingManager.setValidateAfterInactivity(5000);

		return HttpClientBuilder.create().setDefaultRequestConfig(config).setConnectionManager(poolingManager).build();

	}

	private String getBodyAsString(RequestBuilder reqBuilder) throws JsonProcessingException {
		String body = "";
		if (reqBuilder.getRequestBody() != null) {
			body = reqBuilder.getRequestBody();
		} else {
			if (Objects.nonNull(reqBuilder.getRequestObject())) {
				if (reqBuilder.getContentType().equals(JSON)) {
					body = ConvertionUtil.convertPojoToJson(reqBuilder.getRequestObject());
				} else if (reqBuilder.getContentType().equals(XML)) {
					body = ConvertionUtil.convertPojoToXml(reqBuilder.getRequestObject());
				} else {
					throw new IllegalArgumentException("Unsupported content type");
				}
			}
		}

		return body;
	}

	private ApiOutput transformResponse(CloseableHttpResponse response) throws ParseException, IOException {
		Preconditions.checkNotNull(response);
		String responseString = response.getEntity() == null ? "" : EntityUtils.toString(response.getEntity());
		int responseCode = response.getStatusLine().getStatusCode();
		Header[] headers = response.getAllHeaders();
		Map<String, String> headMap = null;
		if (ArrayUtils.isNotEmpty(headers)) {
			headMap = Arrays.stream(headers).collect(Collectors.toMap(Header::getName, Header::getValue));
		}
		return new ApiOutput(responseString, responseCode, headMap);
	}

	private CloseableHttpResponse executeRequest(RequestBuilder reqBuilder, HttpRequestBase baseRequest)
			throws URISyntaxException, ClientProtocolException, IOException {

		URI uri = new URI(getFullURL.apply(reqBuilder).toString());
		LOGGER.info("URL is:\t" + uri.toString());
		baseRequest.setURI(uri);

		clientContext = HttpClientContext.create();

		if (reqBuilder.getCookies() != null && !reqBuilder.getCookies().isEmpty()) {
			BasicCookieStore cookieStore = new BasicCookieStore();
			for (Map.Entry<String, String> entry : reqBuilder.getCookies().entrySet()) {
				BasicClientCookie cookie = new BasicClientCookie(entry.getKey(), entry.getValue());
				cookie.setPath("/");
				cookieStore.addCookie(cookie);
			}
			clientContext.setCookieStore(cookieStore);
		}

		Map<ApiHeaders, String> reqHeaders = Maps.newHashMap();
		if (authToken != null && authToken != "") {
			LOGGER.info("Auth Token: " + authToken);
			reqHeaders.put(ApiHeaders.AUTH, "Token " + authToken);
		} else 
			LOGGER.info("Auth token is not provided");
		
		//String authentication = getAuthHeader(superUsername, superPassword);
		//reqHeaders.put(ApiHeaders.AUTH, authentication);
		
		reqHeaders.put(ApiHeaders.CONTENT_TYPE, reqBuilder.getContentType().getContentType());
		if (reqBuilder.getReqHeaders() != null && !reqBuilder.getReqHeaders().isEmpty())
			reqHeaders.putAll(reqBuilder.getReqHeaders());
		for (Map.Entry<ApiHeaders, String> entry : reqHeaders.entrySet()) {
			baseRequest.setHeader(entry.getKey().getHeader(), entry.getValue());
		}

		return client.execute(baseRequest, clientContext);

	}

	@SuppressWarnings("unused")
	private String getAuthHeader(String username, String password) {
		LOGGER.info(String.format("Authentication Details: Username = %s : Password = %s", username, password));
		String authH = String.format("%s:%s", username, password);
		byte[] encodedAuthH = Base64.getEncoder().encode(authH.getBytes(UTF_8));
		return "Basic " + new String(encodedAuthH);
	}

	private Function<RequestBuilder, URL> getFullURL = builder -> {

		StringBuilder sb = new StringBuilder(baseURL);
		sb.append(builder.getRequestUrl());
		if (builder.getPathParameters() != null && !builder.getPathParameters().isEmpty())
			for (String str : builder.getPathParameters())
				sb.append("/").append(str);

		if (builder.getQueryParameters() != null && !builder.getQueryParameters().isEmpty()) {
			sb.append("?");
			List<NameValuePair> pair = new ArrayList<NameValuePair>();
			builder.getQueryParameters().forEach((key, value) -> {
				pair.add(new BasicNameValuePair(key, value));
			});
			sb.append(URLEncodedUtils.format(pair, UTF_8));
		}

		try {
			return new URL(sb.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return null;
	};

}
