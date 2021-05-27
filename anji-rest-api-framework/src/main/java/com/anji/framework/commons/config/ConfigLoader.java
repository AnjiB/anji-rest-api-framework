package com.anji.framework.commons.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

//import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;

import org.yaml.snakeyaml.Yaml;


/**
 * Loads the configuration from appConfiguration.yaml file
 * 
 * @author anji.boddupally
 *
 */
public class ConfigLoader {
	
	private static final Logger LOGGER = Logger.getLogger(ConfigLoader.class);

	private static Map<String, String> config = new HashMap<String, String>();


	/**
	 * Loads the configuration from appConfiguration.yaml file
	 */
	public static void loadCongifuration() {

		String autEnvironment = System.getProperty("autEnvironment");
		if (autEnvironment == null) {
			autEnvironment = "DEV";
		}
		if (autEnvironment.equalsIgnoreCase("STG") || autEnvironment.equalsIgnoreCase("STAGING")) {
			autEnvironment = "QA";
		} else if ((autEnvironment.equalsIgnoreCase("DEV")) || (autEnvironment.equalsIgnoreCase("DEVELOPMENT"))) {
			autEnvironment = "DEV";
		}

		config.put("AUT_ENVIRONMENT", autEnvironment);

		loadAppConfiguration(autEnvironment);
	}

	/**
	 * Returns the map of key value pair of configuration in yaml file.
	 * 
	 * @return Map
	 */
	public static Map<String, String> getConfig() {
		if (config.isEmpty())
			loadCongifuration();
		return config;
	}

	@SuppressWarnings("unchecked")
	private static void loadAppConfiguration(String autEnvironment) {
		Yaml yaml = new Yaml();
		try {
			InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("appConfiguration.yaml");
			Map<String, Map<String, String>> YFile = (Map<String, Map<String, String>>) yaml.load(input);
			// addToConfig(YFile.get("APP"));
			addToConfig(YFile.get(autEnvironment));
		} catch (org.yaml.snakeyaml.error.YAMLException ye) {
			LOGGER.error(ye.getMessage());
		}
	}


	private static void addToConfig(Map<String, String> m) {
		Iterator<String> iterator = m.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			config.put(key, m.get(key));
		}
	}
	
	public static String getBaseUrl() {
		return getConfig().get("BASE_URL");
	}
}
