package com.info.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.info.common.dao.BaseJdbcTemplate;
import com.info.dto.ViewAppOrgUser;

@Repository
public class AppOrgUserDao extends BaseJdbcTemplate {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getList() {
		String sqlString = "select * from view_apporguser";
		String sql="select f_id,f_name,f_date from view_test";
		List tList = QueryForList(sql);
		for(int i=0;i<tList.size();i++){
			Map map =(Map) tList.get(i);
			System.out.println(map.get("f_id"));
		}
		//List bblist = QueryForList(sqlString,ViewAppOrgUser.class);
		
		//List bList =getJdbcTemplate().queryForList(sqlString,ViewAppOrgUser.class);
		
		List list=getJdbcTemplate().query(sqlString, new RowMapper(){
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				ViewAppOrgUser user =new ViewAppOrgUser();
				user.setF_Id(rs.getInt("f_id"));
				user.setF_Name(rs.getString("f_name"));
				user.setF_Parent_Id(rs.getInt("f_parent_id"));
				user.setF_Sort(rs.getInt("f_sort"));
				
				return user;
			}
		});
		return list;
	}
}
