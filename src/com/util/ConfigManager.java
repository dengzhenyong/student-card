package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	public static Properties loadProperties(String propertiesFileName) {
		InputStream is = null;
		Properties p = null;
		try {
			is = ConfigManager.class.getClassLoader().getResourceAsStream(propertiesFileName);
			p = new Properties();
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p;
	}
}
