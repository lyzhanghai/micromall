package com.micromall.repository;

import com.github.pagehelper.Page;
import com.micromall.entity.DistributionRelation;
import com.micromall.service.vo.DistributionMember;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributionRelationMapper extends BaseMapper<DistributionRelation> {

	Page<DistributionMember> selectLowerMembers(@Param("uid") int uid, @Param("level") Integer level, RowBounds bounds);

	Object lowerMemberStat(int uid);
}