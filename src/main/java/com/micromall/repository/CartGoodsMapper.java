package com.micromall.repository;

import com.micromall.entity.CartGoods;
import com.micromall.service.vo.CartGoodsDTO;
import com.micromall.utils.Condition;
import com.sun.tools.javac.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface CartGoodsMapper extends BaseMapper<CartGoods> {

	@Deprecated
	int updateByPrimaryKey(CartGoods record);

	@Deprecated
	int deleteByPrimaryKey(Integer id);

	@Deprecated
	CartGoods selectByPrimaryKey(Integer id);

	@Deprecated
	List<CartGoods> selectPageByWhereClause(Condition condition, RowBounds bounds);

	@Select("SELECT t2.id goodsId, t2.title, t2.main_image as image, t2.price, t1.buy_number as buyNumber, t2.inventory FROM cart_goods t1 RIGHT"
			+ " JOIN goods t2 ON t1.goods_id = t2.id WHERE t1.uid = #{uid} AND t2.deleted = 0  ORDER BY t1.id DESC")
	@ResultType(CartGoodsDTO.class)
	List<CartGoodsDTO> listGoods(@Param("uid") int uid);

}