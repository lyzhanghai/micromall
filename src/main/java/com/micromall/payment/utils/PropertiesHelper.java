package com.micromall.payment.utils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PropertiesHelper {

	public final static ConcurrentMap<String, Properties> propertiesMap = new ConcurrentHashMap<String, Properties>();

	private static String propertiesDir;

	public static Properties get(String key) {
		if (!propertiesMap.containsKey(key)) {
			synchronized (propertiesMap) {
				if (!propertiesMap.containsKey(key)) {
					Properties propertie = new Properties();
					FileInputStream fis = null;
					try {
						fis = new FileInputStream(new File(propertiesDir, key + ".properties"));
						propertie.load(fis);
						propertiesMap.put(key, propertie);
					} catch (IOException e) {
						throw new RuntimeException(e);
					} finally {
						IOUtils.closeQuietly(fis);
					}

				}
			}
		}

		return propertiesMap.get(key);
	}

	public static void setPropertiesDir(String propertiesDir) {
		PropertiesHelper.propertiesDir = propertiesDir;
	}
}
