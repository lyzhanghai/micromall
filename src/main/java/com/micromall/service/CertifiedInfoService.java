package com.micromall.service;

import com.micromall.repository.CertifiedInfoMapper;
import com.micromall.repository.entity.CertifiedInfo;
import com.micromall.repository.entity.common.CertifiedStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/19.
 */
@Service
@Transactional
public class CertifiedInfoService {

	@Resource
	private CertifiedInfoMapper mapper;

	@Transactional(readOnly = true)
	public CertifiedInfo getCertifiedInfo(int uid) {
		return mapper.selectByPrimaryKey(uid);
	}

	public void insert(CertifiedInfo certifiedInfo) {
		mapper.insert(certifiedInfo);
	}

	public boolean update(CertifiedInfo certifiedInfo) {
		return mapper.updateByPrimaryKey(certifiedInfo) > 0;
	}

	public boolean audit(int uid, boolean agreed, String auditlog) {
		CertifiedInfo certifiedInfo = new CertifiedInfo();
		certifiedInfo.setUid(uid);
		if (agreed) {
			certifiedInfo.setStatus(CertifiedStatus.审核通过);
			certifiedInfo.setAuditlog("审核通过");
		} else {
			certifiedInfo.setAuditlog(auditlog);
		}
		certifiedInfo.setAuditTime(new Date());
		return mapper.updateByPrimaryKey(certifiedInfo) > 0;
	}

	@Transactional(readOnly = true)
	public boolean certified(int uid) {
		CertifiedInfo certifiedInfo = mapper.selectByPrimaryKey(uid);
		return certifiedInfo != null && certifiedInfo.getStatus() == CertifiedStatus.审核通过;
	}

}
