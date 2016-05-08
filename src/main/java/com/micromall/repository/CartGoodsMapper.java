package com.micromall.repository;

import com.micromall.repository.entity.CartGoods;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartGoodsMapper extends BaseMapper<CartGoods> {

	List<CartGoods> selectMemberCartGoods(int uid);

}