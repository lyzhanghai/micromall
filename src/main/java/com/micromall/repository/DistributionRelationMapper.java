package com.micromall.repository;

import com.micromall.entity.DistributionRelation;
import com.micromall.utils.Condition;
import com.sun.tools.javac.util.List;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributionRelationMapper extends BaseMapper<DistributionRelation> {

	@Deprecated
	int updateByPrimaryKey(DistributionRelation record);

	@Deprecated
	int deleteByPrimaryKey(Object id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	@Deprecated
	DistributionRelation selectByPrimaryKey(Object id);
}