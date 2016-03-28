package com.micromall.service;

import com.micromall.repository.FavoriteRepository;
import com.micromall.service.vo.FavoriteGoods;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangzx on 16/3/26.
 */
@Service
public class FavoriteService {
	@Resource
	private FavoriteRepository favoriteRepository;

	public List<FavoriteGoods> goodses(int uid) {
		return null;
	}

	public boolean joinFavorite(int uid, int goodsId) {
		return false;
	}

	public boolean deleteGoods(int uid, int goodsId) {
		return false;
	}
}
