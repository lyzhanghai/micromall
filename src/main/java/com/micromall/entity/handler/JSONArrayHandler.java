package com.micromall.entity.handler;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JSONArrayHandler extends BaseTypeHandler<String[]> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
		if (jdbcType == null) {
			ps.setObject(i, JSON.toJSONString(parameter));
		} else {
			ps.setObject(i, JSON.toJSONString(parameter), jdbcType.TYPE_CODE);
		}
	}

	public static void main(String[] args) {
		System.out.println(String[].class.getName());
	}

	@Override
	public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String value = rs.getString(columnName);
		return StringUtils.isBlank(value) ? new String[0] : JSON.parseObject(value, String[].class);
	}

	@Override
	public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String value = rs.getString(columnIndex);
		return StringUtils.isBlank(value) ? new String[0] : JSON.parseObject(value, String[].class);
	}

	@Override
	public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String value = cs.getString(columnIndex);
		return StringUtils.isBlank(value) ? new String[0] : JSON.parseObject(value, String[].class);
	}

}
