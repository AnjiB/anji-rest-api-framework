package com.anji.framework.api.impl;

import java.util.concurrent.TimeUnit;

import com.anji.framework.api.builder.RequestBuilder;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class ClientService {

	static Client getClient(RequestBuilder builder) throws Exception {

		Client client = null;

		ClientServiceCache cache = ClientServiceCache.getInstance();

		if (builder.isClientCached()) {
			client = cache.getClient(builder.getUsername());
			if (client == null) {
				client = new Client(builder.getUsername(), builder.getPassword(), builder.isAuthRequired());
			}
			cache.add(builder.getUsername(), client);
		} else {
			cache.remove(builder.getUsername());
			client = new Client(builder.getUsername(), builder.getPassword(), builder.isAuthRequired());
		}
		return client;
	}

	private static class ClientServiceCache {

		Cache<String, Client> cliCache;

		private ClientServiceCache() {
			cliCache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.MINUTES).maximumSize(10).build();
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
