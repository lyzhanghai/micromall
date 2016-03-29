package com.micromall.repository;

import com.micromall.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangzx on 16/3/23.
 */
@Repository
public interface ArticleRepository {

	@Select("SELECT `id`,`type`,`title`,`content`,`create_time` as createTime FROM article WHERE `type`=#{type} ORDER BY `create_time` DESC")
	@ResultType(Article.class)
	List<Article> select(@Param("type") int type, RowBounds bounds);

}
