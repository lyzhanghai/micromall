/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/05/12.
 */
package com.micromall.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * MyBaits XML Mapper 动态加载组件
 *
 * @author ciwei@xiaokakeji.com
 * @date 2016/05/12.
 */
@Component
public class XMLMapperDynamicLoader implements DisposableBean, InitializingBean, ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(XMLMapperDynamicLoader.class);

	private ApplicationContext context;
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public void destroy() throws Exception {
		executor.shutdownNow();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (CommonEnvConstants.ENV.isDevEnv()) {
			logger.info("启动MyBatis Mapper配置并更动态加载监听器....");
			executor.scheduleAtFixedRate(new ScannerTask(), 5, 5, TimeUnit.SECONDS);
		}
	}

	class ScannerTask implements Runnable {

		private Map<String, String> mapperMonitor = new HashMap<>();
		private Resource[]    mapperResources;
		private Configuration configuration;

		public ScannerTask() throws NoSuchFieldException, IllegalAccessException, IOException {
			SqlSessionFactoryBean factoryBean = context.getBean(SqlSessionFactoryBean.class);
			this.mapperResources = getFieldValue(factoryBean, "mapperLocations");

			SqlSessionFactory factory = context.getBean(SqlSessionFactory.class);
			this.configuration = factory.getConfiguration();

			if (ArrayUtils.isNotEmpty(this.mapperResources)) {
				for (Resource resource : this.mapperResources) {
					mapperMonitor.put(resource.getFilename(), getMultiKey(resource));
				}
			}
		}

		@Override
		public void run() {
			try {
				if (isChanged() && ArrayUtils.isNotEmpty(this.mapperResources)) {
					logger.info("MyBatis Mapper配置有变动，开始重新加载");
					cleanOldConfig();
					for (Resource resource : this.mapperResources) {
						if (resource == null) {
							continue;
						}

						try {
							XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(resource.getInputStream(), configuration, resource.toString(),
									configuration.getSqlFragments());
							xmlMapperBuilder.parse();
						} catch (Exception e) {
							logger.error("Failed to parse mapping resource: '" + resource + "'", e);
							break;
						} finally {
							ErrorContext.instance().reset();
						}
						logger.info("Parsed mapper file: '" + resource + "'");
					}
					logger.info("MyBatis Mapper配置加载完成");
				}
			} catch (Exception e) {
				logger.error("Failed to dynamic loader mapping resource", e);
			}
		}

		private void cleanOldConfig() throws NoSuchFieldException, IllegalAccessException {
			((Map)getFieldValue(configuration, "mappedStatements")).clear();
			((Map)getFieldValue(configuration, "caches")).clear();
			((Map)getFieldValue(configuration, "resultMaps")).clear();
			((Map)getFieldValue(configuration, "parameterMaps")).clear();
			((Map)getFieldValue(configuration, "keyGenerators")).clear();
			((Map)getFieldValue(configuration, "sqlFragments")).clear();
			((Set)getFieldValue(configuration, "loadedResources")).clear();
			logger.info("清除MyBatis Configuration 原有Mapper配置信息");
		}

		private <T> T getFieldValue(Object object, String fieldName) throws IllegalAccessException, NoSuchFieldException {
			Field field = object.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return (T)field.get(object);
		}

		private String getMultiKey(Resource resource) throws IOException {
			String contentLength = String.valueOf((resource.contentLength()));
			String lastModified = String.valueOf((resource.lastModified()));
			return new StringBuilder(contentLength).append(lastModified).toString();
		}

		public boolean isChanged() throws IOException {
			boolean changed = false;
			if (ArrayUtils.isNotEmpty(this.mapperResources)) {
				for (Resource resource : this.mapperResources) {
					String _old_multiKey = mapperMonitor.get(resource.getFilename());
					String multiKey = getMultiKey(resource);
					if (!multiKey.equals(_old_multiKey)) {
						changed = true;
						mapperMonitor.put(resource.getFilename(), multiKey);
					}
				}
			}
			return changed;
		}
	}
}
