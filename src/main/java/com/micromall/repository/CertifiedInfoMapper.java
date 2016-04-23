/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * Created by ciwei@xiaokakeji.com on 2016/04/19.
 */
package com.micromall.repository;

import com.github.pagehelper.Page;
import com.micromall.entity.CertifiedInfo;
import com.micromall.utils.Condition;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

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
	Page<CertifiedInfo> selectPageByWhereClause(Condition condition, RowBounds bounds);

}
