package com.micromall.repository;

import com.micromall.repository.entity.CartGoods;
import com.sun.tools.javac.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CartGoodsMapper extends BaseMapper<CartGoods> {

	List<CartGoods> selectMemberCartGoods(int uid);

}