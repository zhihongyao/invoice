package com.invoice.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyUtil extends PropertyPlaceholderConfigurer {

	private static Map<String, String> map;
	
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		map = new HashMap<String, String>();
		for (Object obj : props.keySet()) {
			String key = obj.toString();
			String value = props.getProperty(key);
			map.put(key, value);
		}
	}
	
	public static String getProperty(String key) {
		return map.get(key);
	}
	
	public static Map<String, Object> getDomainsMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.APPID, getProperty(Constants.APPID));
		map.put(Constants.APPSECRET, getProperty(Constants.APPSECRET));
		return map;
	}
    
}
