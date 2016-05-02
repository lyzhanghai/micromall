package com.micromall.service;

import com.micromall.entity.DistributionRelation;
import com.micromall.entity.Member;
import com.micromall.repository.DistributionRelationMapper;
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
		if (member != null) {
			DistributionRelation relation = new DistributionRelation(member.getId(), lowerUid, 1, new Date());
			mapper.insert(relation);
			if (member.getParentUid() != null) {
				relation = new DistributionRelation(member.getParentUid(), lowerUid, 2, new Date());
				mapper.insert(relation);
			}
		}
	}
}
