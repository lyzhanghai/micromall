package com.micromall.repository;

import com.micromall.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<Goods> {

	Object distributionOrderAmountSum(@Param("uid") int uid, @Param("level") int level);
}