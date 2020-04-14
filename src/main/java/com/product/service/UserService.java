package com.product.service;

import com.product.model.TUser;

public interface UserService {

	public int addUser(TUser tUser);
	
	public String queryUser(String openId);
}
