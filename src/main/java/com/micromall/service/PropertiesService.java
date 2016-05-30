package com.micromall.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.micromall.repository.PropertiesMapper;
import com.micromall.repository.entity.Properties;
import org.apache.commons.collections.ListUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by zhangzx on 16/4/6.
 */
@Service
@Transactional(readOnly = true)
public class PropertiesService {

	private static PropertiesService instance;
	@Resource
	private        PropertiesMapper  mapper;

	private Map<String, Properties> propertiesMap = Maps.newHashMap();
	private ReentrantReadWriteLock  lock          = new ReentrantReadWriteLock();

	public static PropertiesService getInstance() {
		return instance;
	}

	@PostConstruct
	public void init() {
		instance = this;
		reload();
	}

	@Scheduled(cron = "0/30 * * * * ?")
	public void reload() {
		List<Properties> properties = mapper.selectAllProperties();

		lock.writeLock().lock();
		try {
			propertiesMap.clear();
			for (Properties prop : properties) {
				propertiesMap.put(prop.getName(), prop);
			}
		} finally {
			lock.writeLock().unlock();
		}
	}

	public Properties getProperties(String key) {
		lock.readLock().lock();
		try {
			// return mapper.selectByPrimaryKey(key);
			return propertiesMap.get(key);
		} finally {
			lock.readLock().unlock();
		}
	}

	public String getString(String key) {
		Properties properties = this.getProperties(key);
		return properties != null ? properties.getContent() : null;
	}

	public Boolean getBoolean(String key) {
		Properties properties = this.getProperties(key);
		return properties != null ? Boolean.parseBoolean(properties.getContent()) : null;
	}

	public Integer getInteger(String key) {
		Properties properties = this.getProperties(key);
		return properties != null ? Integer.parseInt(properties.getContent()) : null;
	}

	public Long getLong(String key) {
		Properties properties = this.getProperties(key);
		return properties != null ? Long.parseLong(properties.getContent()) : null;
	}

	public <T> T getJSONObject(String key, Class<T> clazz) {
		Properties properties = this.getProperties(key);
		return properties != null ? JSON.parseObject(properties.getContent(), clazz) : null;
	}

	public <T> List<T> getJSONList(String key, Class<T> clazz) {
		Properties properties = this.getProperties(key);
		return properties != null ? JSON.parseArray(properties.getContent(), clazz) : null;
	}

	@Transactional
	public void multiUpdates(Properties... props) {
		for (Properties prop : props) {
			mapper.updateByPrimaryKey(prop);
		}
		reload();
	}

	public List<Properties> listProperties() {
		return ListUtils.unmodifiableList(Lists.newArrayList(propertiesMap.values()));
	}
}
