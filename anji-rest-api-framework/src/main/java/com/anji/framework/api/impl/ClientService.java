package com.anji.framework.api.impl;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.anji.framework.api.builder.RequestBuilder;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * This is a service class which gives the client. It also cache the client 
 * and helps us to re-use the same client if needed.
 * 
 * @author boddupally.anji
 */
public class ClientService {

	private static final Logger LOGGER = Logger.getLogger(ClientService.class);
	
	private static ClientServiceCache cache = ClientServiceCache.getInstance();
	
	static Client getClient(RequestBuilder builder) throws Exception {

		Client client = null;

		

		if (builder.isClientCached()) {
			client = cache.getClient(builder.getUsername());
			if (client == null) {
				client = new Client(builder.getUsername(), builder.getPassword(), builder.isAuthRequired());
				cache.add(builder.getUsername(), client);
				LOGGER.info("Cached the client for the user: " + builder.getUsername());
			} else {
				LOGGER.info("Obtained the client from the cache for the user: " + builder.getUsername());
			}
			
			
		} else {
			if(builder.getUsername() != null)
				cache.remove(builder.getUsername());
			client = new Client(builder.getUsername(), builder.getPassword(), builder.isAuthRequired());
		}
		return client;
	}

	private static class ClientServiceCache {

		Cache<String, Client> cliCache;

		private ClientServiceCache() {
			cliCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).maximumSize(10).build();
		}

		public void add(final String username, final Client client) {
			Preconditions.checkNotNull(username);
			cliCache.put(username, client);
		}

		public Client getClient(final String username) {
			return cliCache.getIfPresent(username);
		}

		public void remove(final String username) {
			cliCache.invalidate(username);
		}

		public static ClientServiceCache getInstance() {
			return new ClientServiceCache();
		}
	}
}
