package com.micromall.repository;

import com.github.pagehelper.Page;
import com.micromall.repository.entity.Properties;
import com.micromall.utils.Condition;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertiesMapper extends BaseMapper<Properties> {

	@Update("update properties set content = #{content} where name = #{name}")
	int updateByPrimaryKey(Properties record);

	@Select("select name, content from properties")
	@ResultType(Properties.class)
	List<Properties> selectAllProperties();

	@Select("select name, content from properties where name = #{name}")
	@ResultType(Properties.class)
	Properties selectByPrimaryKey(Object id);

	@Deprecated
	int insert(Properties record);

	@Deprecated
	int deleteByPrimaryKey(Object id);

	@Deprecated
	int deleteByWhereClause(Condition condition);

	@Deprecated
	List<Properties> selectMultiByWhereClause(Condition condition);

	@Deprecated
	Properties selectOneByWhereClause(Condition condition);

	@Deprecated
	Page<Properties> selectPageByWhereClause(Condition condition, RowBounds bounds);

	@Deprecated
	int countByWhereClause(Condition condition);

}