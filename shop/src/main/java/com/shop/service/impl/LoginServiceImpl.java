package com.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.shop.common.JwtUtils;
import com.shop.dao.UserDao;
import com.shop.model.Users;
import com.shop.service.LoginService;
import com.shop.vo.UserLoginByEmailVO;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	UserDao userDao;

	@Autowired
	JwtUtils jwtUtils;

	@Override
	public String loginByEmail(UserLoginByEmailVO vo) throws JOSEException {
		Users user = userDao.findUserByEmail(vo.getEmail());
		if (user == null) {
			return null;
		}
		if (!vo.getPassword().equals(user.getPassword())) {
			return null;
		}
		return jwtUtils.generateTokenLogin(user.getId());
	}

}
