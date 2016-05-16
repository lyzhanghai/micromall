package com.micromall.repository;

import com.github.pagehelper.Page;
import com.micromall.repository.entity.DistributionRelation;
import com.micromall.service.vo.DistributionMember;
import com.micromall.utils.Condition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistributionRelationMapper extends BaseMapper<DistributionRelation> {

	Page<DistributionMember> selectLowerMembers(@Param("uid") int uid, @Param("level") Integer level, RowBounds bounds);

	int lowerMemberStat(@Param("uid") int uid, @Param("level") Integer level);

	@Deprecated
	int updateByPrimaryKey(DistributionRelation record);

	@Deprecated
	int deleteByPrimaryKey(Object id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	@Deprecated
	DistributionRelation selectByPrimaryKey(Object id);

	@Deprecated
	DistributionRelation selectOneByWhereClause(Condition condition);

	@Deprecated
	List<DistributionRelation> selectMultiByWhereClause(Condition condition);

	@Deprecated
	Page<DistributionRelation> selectPageByWhereClause(Condition condition, RowBounds bounds);

}