package com.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.shop.common.Constant;
import com.shop.response.ResponseData;
import com.shop.service.LoginService;
import com.shop.vo.UserLoginByEmailVO;

@RestController
@RequestMapping("/users")
public class LoginController {

	@Autowired
	LoginService loginService;

	@PostMapping(value = "/login")
	public ResponseEntity<ResponseData<String>> login(@RequestBody UserLoginByEmailVO vo) throws JOSEException {
		ResponseData<String> respone = new ResponseData<String>();
		String token = loginService.loginByEmail(vo);
		if (token != null) {
			respone.setData(token);
			respone.setStatus(Constant.OK);
			return new ResponseEntity<ResponseData<String>>(respone, HttpStatus.OK);
		} else {
			respone.setStatus(Constant.FAILED);
			return new ResponseEntity<ResponseData<String>>(respone, HttpStatus.BAD_REQUEST);
		}
	}
}
