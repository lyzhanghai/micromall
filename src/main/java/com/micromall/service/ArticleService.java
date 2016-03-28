package com.micromall.service;

import com.micromall.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class ArticleService {
	@Resource
	private ArticleRepository articleRepository;
}
