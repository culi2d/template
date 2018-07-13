package com.shop.dao;

import com.shop.model.Users;

public interface UserDao {
	Users findUserById(long id);

	Users findUserByEmail(String email);

}
