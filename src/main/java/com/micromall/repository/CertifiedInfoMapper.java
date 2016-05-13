package com.micromall.repository;

import com.github.pagehelper.Page;
import com.micromall.repository.entity.CertifiedInfo;
import com.micromall.utils.Condition;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2016/04/19.
 */
@Repository
public interface CertifiedInfoMapper extends BaseMapper<CertifiedInfo> {

	@Deprecated
	int deleteByPrimaryKey(Object id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	@Deprecated
	CertifiedInfo selectOneByWhereClause(Condition condition);

	@Deprecated
	List<CertifiedInfo> selectMultiByWhereClause(Condition condition);

	@Deprecated
	Page<CertifiedInfo> selectPageByWhereClause(Condition condition, RowBounds bounds);

	@Deprecated
	int countByWhereClause(Condition condition);
}
