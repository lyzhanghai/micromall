/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/04/19.
 */
package com.micromall.service;

import com.micromall.entity.CertifiedInfo;
import com.micromall.repository.CertifiedInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/19.
 */
@Service
public class CertifiedInfoService {

	@Resource
	private CertifiedInfoMapper mapper;

	public CertifiedInfo getCertifiedInfo(int uid) {
		return mapper.selectByPrimaryKey(uid);
	}

	public void insert(CertifiedInfo certifiedInfo) {
		mapper.insert(certifiedInfo);
	}

	public boolean update(CertifiedInfo certifiedInfo) {
		return mapper.updateByPrimaryKey(certifiedInfo) > 0;
	}
}
