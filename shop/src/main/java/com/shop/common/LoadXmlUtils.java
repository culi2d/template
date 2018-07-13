package com.shop.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class LoadXmlUtils {
	public String loadQuery(final String xmlFilePath, final String key) {
		String result = "";
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(xmlFilePath);
			Properties properties = new Properties();
			properties.loadFromXML(is);
			result = properties.getProperty(key).trim();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
