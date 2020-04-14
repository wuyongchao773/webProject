package com.product.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.product.model.TUser;
import com.product.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int addUser(TUser tUser) {
		String insertSql = "insert into T_USER(id,openid,real_name,sex,language,city,county,province,headUrl,userType,role_type,is_vip,subscribe,createTime,delete_status)";
		insertSql +="value(?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?)";
		int len = jdbcTemplate.update(insertSql,tUser.getId(),tUser.getOpenId(),tUser.getRealName(),
				tUser.getSex(), tUser.getLanguage(), tUser.getCity(), tUser.getCounty(), tUser.getProvince(),
				tUser.getHeadUrl(), tUser.getUserType(), tUser.getRoleType(),tUser.getIsVip(), tUser.getSubscribe(),tUser.getDelete_status());
		return len;
	}

	@Override
	public String queryUser(String openId) {
		String querySql = " select id as id from T_USER where 1=1 and openId = ? ";
		List<Map<String, Object>> lists = jdbcTemplate.queryForList(querySql, new Object[] { openId });
		if (lists.size() > 0) {
			return String.valueOf(lists.get(0).get("id"));
		} else {
			return null;
		}
	}
}
