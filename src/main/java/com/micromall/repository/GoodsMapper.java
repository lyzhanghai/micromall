package com.micromall.repository;

import com.micromall.repository.entity.Goods;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsMapper extends BaseMapper<Goods> {

	Goods selectFullByPrimaryKey(int goodsId);
}