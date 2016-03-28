package com.micromall.service;

import com.micromall.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
@Transactional
public class CategoryService {
	@Resource
	private CategoryRepository categoryRepository;
}
