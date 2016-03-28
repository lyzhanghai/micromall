package com.micromall.service;

import com.micromall.repository.GoodsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class GoodsService {
	@Resource
	private GoodsRepository goodsRepository;

}
