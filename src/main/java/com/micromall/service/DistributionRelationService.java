package com.micromall.service;

import com.micromall.repository.DistributionRelationMapper;
import com.micromall.repository.entity.DistributionRelation;
import com.micromall.repository.entity.Member;
import com.micromall.utils.Condition.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/18.
 */
@Service
public class DistributionRelationService {

	@Resource
	private DistributionRelationMapper mapper;
	@Resource
	private MemberService              memberService;

	@Transactional
	public void relation(int uid, int lowerUid) {
		Member member = memberService.get(uid);
		if (mapper.countByWhereClause(Criteria.create().andEqualTo("uid", member.getId()).andEqualTo("lower_uid", lowerUid).build()) == 0) {
			DistributionRelation relation = new DistributionRelation(member.getId(), lowerUid, 1, new Date());
			mapper.insert(relation);
		}
		if (member.getParentUid() != null
				&& mapper.countByWhereClause(Criteria.create().andEqualTo("uid", member.getParentUid()).andEqualTo("lower_uid", lowerUid).build())
				== 0) {
			DistributionRelation relation = new DistributionRelation(member.getParentUid(), lowerUid, 2, new Date());
			mapper.insert(relation);
		}
	}
}
