package com.info.common.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseJdbcTemplate {
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings("rawtypes")
	public List QueryForList(String SQL) {
		return jdbcTemplate.queryForList(SQL);
	}

	@SuppressWarnings({ "rawtypes" })
	public List QueryForList(String SQL, Class<?> className) {
		return jdbcTemplate.queryForList(SQL,
				BeanPropertyRowMapper.newInstance(className));
	}

	public int getCount(String SQL) {
		int count = jdbcTemplate.queryForInt(SQL);
		return count;
	}

	@SuppressWarnings("rawtypes")
	public List getListForPage(int start, int limit, String SQL) {
		int lastIndex = start + limit;
		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		paginationSQL.append(SQL);
		paginationSQL.append("　) temp where ROWNUM <= " + lastIndex);
		paginationSQL.append(" ) WHERE　num > " + start);
		return jdbcTemplate.queryForList(paginationSQL.toString());
	}
	public int getTotalProperty(String SQL) {
		StringBuilder countBuilder=new StringBuilder();
		countBuilder.append("select count(*) from (");
		countBuilder.append(SQL);
		countBuilder.append(")");
		return getCount(countBuilder.toString());
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
