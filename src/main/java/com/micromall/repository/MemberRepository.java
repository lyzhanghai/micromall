package com.micromall.repository;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhangzx on 16/3/23.
 */
public interface MemberRepository {

	@Select("")
	List<Object> selectAll();

}
