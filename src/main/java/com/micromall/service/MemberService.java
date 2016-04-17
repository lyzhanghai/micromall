package com.micromall.service;

import com.micromall.entity.Member;
import com.micromall.entity.ext.MemberLevels;
import com.micromall.repository.MemberMapper;
import com.micromall.utils.CommonEnvConstants;
import com.micromall.utils.Condition;
import com.micromall.utils.Condition.Criteria;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class MemberService {

	@Resource
	private MemberMapper                mapper;
	@Resource
	private CashAccountService          cashAccountService;
	@Resource
	private DistributionRelationService distributionRelationService;

	@Transactional
	public boolean update(Member member) {
		member.setUpdateTime(new Date());
		return mapper.updateByPrimaryKey(member) > 0;
	}

	@Transactional
	public Member registerForPhone(String phone, String usePromoteCode) {
		return _create(null, phone, usePromoteCode);
	}

	@Transactional
	public Member registerForWechatId(String wechatId) {
		return _create(wechatId, null, null);
	}

	private Member _create(String wechatId, String phone, String usePromoteCode) {
		Member member = new Member();
		member.setPhone(phone);
		member.setWechatId(wechatId);
		if (StringUtils.isNotEmpty(phone)) {
			member.setNickname(phone);
		}
		member.setAvatar(CommonEnvConstants.MEMBER_DEFAULT_AVATAR);
		member.setLevel(MemberLevels.LV_0);
		member.setVerified(false);
		member.setDeleted(false);
		member.setRegisterTime(new Date());
		// 关联上级分销商
		if (StringUtils.isNotEmpty(usePromoteCode)) {
			Member parent = mapper.selectOneByWhereClause(Criteria.create().andEqualTo("my_promote_code", usePromoteCode).build());
			if (parent != null) {
				member.setParentUid(parent.getId());
				member.setUsePromoteCode(usePromoteCode);
			}
		}
		mapper.insert(member);

		// 生成推广码
		Member _updateMember = new Member();
		_updateMember.setId(member.getId());
		_updateMember.setMyPromoteCode(Base64.encodeBase64String(("#" + member.getId()).getBytes()));
		mapper.updateByPrimaryKey(_updateMember);

		// 创建资金账户
		cashAccountService.createCashAccount(member.getId());

		// 建立分销商上下级关系
		if (member.getParentUid() != null) {
			distributionRelationService.relation(member.getParentUid(), member.getId());
		}
		return member;
	}

	@Transactional(readOnly = true)
	public Member findOneByCriteria(Condition condition) {
		return mapper.selectOneByWhereClause(condition);
	}

	@Transactional(readOnly = true)
	public Member get(int id) {
		return mapper.selectByPrimaryKey(id);
	}
}
