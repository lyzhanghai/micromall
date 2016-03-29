package com.micromall.repository;

import com.micromall.entity.Category;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangzx on 16/3/23.
 */
@Repository
public interface CategoryRepository {

	@Select("SELECT `id`, `name`, `index` FROM `category` ORDER BY `index` DESC")
	@ResultType(Category.class)
	List<Category> selectAll();
}
