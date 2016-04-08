package com.micromall.service;

import com.micromall.repository.PropertiesRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/4/6.
 */
@Service
public class PropertiesService {

	@Resource
	private PropertiesRepository propertiesRepository;

	public String get(String key) {
		return propertiesRepository.get(key);
	}
}
