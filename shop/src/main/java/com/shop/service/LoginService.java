package com.shop.service;

import com.nimbusds.jose.JOSEException;
import com.shop.vo.UserLoginByEmailVO;

public interface LoginService {
	String loginByEmail(UserLoginByEmailVO vo) throws JOSEException;
}
