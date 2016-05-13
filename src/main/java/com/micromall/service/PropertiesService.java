package com.micromall.service;

import com.alibaba.fastjson.JSON;
import com.micromall.repository.PropertiesMapper;
import com.micromall.repository.entity.Properties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangzx on 16/4/6.
 */
@Service
public class PropertiesService {

	@Resource
	private PropertiesMapper mapper;

	public Properties getProperties(String key) {
		return mapper.selectByPrimaryKey(key);
	}

	public String getString(String key) {
		Properties properties = mapper.selectByPrimaryKey(key);
		return properties != null ? properties.getContent() : null;
	}

	public Boolean getBoolean(String key) {
		Properties properties = mapper.selectByPrimaryKey(key);
		return properties != null ? Boolean.parseBoolean(properties.getContent()) : null;
	}

	public Integer getInteger(String key) {
		Properties properties = mapper.selectByPrimaryKey(key);
		return properties != null ? Integer.parseInt(properties.getContent()) : null;
	}

	public Long getLong(String key) {
		Properties properties = mapper.selectByPrimaryKey(key);
		return properties != null ? Long.parseLong(properties.getContent()) : null;
	}

	public <T> T getJSONObject(String key, Class<T> clazz) {
		Properties properties = mapper.selectByPrimaryKey(key);
		return properties != null ? JSON.parseObject(properties.getContent(), clazz) : null;
	}

	public <T> List<T> getJSONList(String key, Class<T> clazz) {
		Properties properties = mapper.selectByPrimaryKey(key);
		return properties != null ? JSON.parseArray(properties.getContent(), clazz) : null;
	}

	@Transactional
	public void multiUpdates(Properties... props) {
		for (Properties prop : props) {
			mapper.updateByPrimaryKey(prop);
		}
	}

	public List<Properties> listProperties() {
		return mapper.selectAllProperties();
	}
}
