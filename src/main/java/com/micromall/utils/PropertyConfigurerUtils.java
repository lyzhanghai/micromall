package com.micromall.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangzx on 15/12/24.
 */
public class PropertyConfigurerUtils extends PropertyPlaceholderConfigurer {

	private static Logger     logger               = LoggerFactory.getLogger(PropertyConfigurerUtils.class);
	private static String     PLACE_HOLDER_REGEX   = "\\$\\{([^\\}]+)\\}";
	private static Pattern    PLACE_HOLDER_PATTERN = Pattern.compile(PLACE_HOLDER_REGEX);
	private static Properties props                = new Properties();

	public static String getString(String name) {
		return props.getProperty(name);
	}

	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		logger.info("初始化系统配置属性......");
		Iterator iterator = props.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String)iterator.next();
			String value = props.getProperty(key);
			logger.info("{}={}", key, value);
			if (this.props.containsKey(key)) {
				logger.info("配置属性{}覆盖：{ } >>>> {}  ", key, this.props.getProperty(key), value);
			}
			this.props.put(key, value);
		}
		logger.info("系统配置属性初始化完成.");
	}

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		Matcher matcher = PLACE_HOLDER_PATTERN.matcher(propertyValue);
		for (int i = 1; matcher.find(); i++) {
			String key = matcher.group(i);
			if (System.getProperties().get(key) != null) {
				propertyValue = propertyValue.replaceFirst(PLACE_HOLDER_REGEX, System.getProperties().getProperty(key));
			} else {
				logger.error("找不到配置项：" + propertyValue + " >>>> ${" + key + "}");
			}
		}
		return propertyValue;
	}

	@Override
	public void setLocation(Resource location) {
		super.setLocation(location);
		logger.info("location：", JSON.toJSONString(location));
	}

	@Override
	public void setLocations(Resource... locations) {
		super.setLocations(locations);
		logger.info("locations：", JSON.toJSONString(locations));
	}
}
