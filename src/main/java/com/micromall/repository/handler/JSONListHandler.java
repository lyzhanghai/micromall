package com.micromall.repository.handler;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JSONListHandler extends BaseTypeHandler<List<?>> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<?> parameter, JdbcType jdbcType) throws SQLException {
		if (jdbcType == null) {
			ps.setObject(i, JSON.toJSONString(parameter));
		} else {
			ps.setObject(i, JSON.toJSONString(parameter), jdbcType.TYPE_CODE);
		}
	}

	@Override
	public List<?> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String value = rs.getString(columnName);
		return StringUtils.isBlank(value) ? new ArrayList<Object>() : JSON.parseArray(value);
	}

	@Override
	public List<?> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String value = rs.getString(columnIndex);
		return StringUtils.isBlank(value) ? new ArrayList<Object>() : JSON.parseArray(value);
	}

	@Override
	public List<?> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String value = cs.getString(columnIndex);
		return StringUtils.isBlank(value) ? new ArrayList<Object>() : JSON.parseArray(value);
	}

}
