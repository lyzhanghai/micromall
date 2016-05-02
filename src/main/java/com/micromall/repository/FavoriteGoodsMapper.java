package com.micromall.repository;

import com.micromall.entity.FavoriteGoods;
import com.sun.tools.javac.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteGoodsMapper extends BaseMapper<FavoriteGoods> {

	List<FavoriteGoods> selectMemberFavoriteGoods(int uid);
}