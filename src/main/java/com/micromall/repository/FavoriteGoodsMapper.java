package com.micromall.repository;

import com.micromall.repository.entity.FavoriteGoods;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteGoodsMapper extends BaseMapper<FavoriteGoods> {

	List<FavoriteGoods> selectMemberFavoriteGoods(int uid);
}