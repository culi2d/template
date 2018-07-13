package com.shop.security;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.shop.common.Constant;
import com.shop.common.JwtUtils;
import com.shop.dao.UserDao;
import com.shop.enumerate.UserType;
import com.shop.model.Users;

@Component
public class JwtAuthenticationTokenFilter extends GenericFilterBean {

	/** The jwt service. */
	@Autowired
	private JwtUtils jwtUtils;

	/** The user DAO. */
	@Autowired
	private UserDao userDao;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader(Constant.AUTHORIZATION);

		if (token != null) {
			try {
				long userId = jwtUtils.getUserIdFromToken(token);
				Users user = userDao.findUserById(userId);
				if (user != null) {
					// set user role
					List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
					authorities.add(new SimpleGrantedAuthority(UserType.getByValue(user.getType()).getRole()));
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							user.getId(), null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					Constant.NOT_EXISTS, null, null);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response);
	}
}
