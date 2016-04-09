package com.micromall.service;

import com.micromall.entity.Properties;
import com.micromall.repository.PropertiesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/4/6.
 */
@Service
public class PropertiesService {

	@Resource
	private PropertiesMapper mapper;

	public String get(String key) {
		Properties properties = mapper.selectByPrimaryKey(key);
		return properties != null ? properties.getContent() : null;
	}
}
