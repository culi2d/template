package com.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shop.security.CorsFilter;
import com.shop.security.JwtAuthenticationEntryPoint;
import com.shop.security.JwtAuthenticationTokenFilter;

/**
 * WebSecurityConfig class
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;
	
	@Autowired
	private JwtAuthenticationTokenFilter authenticationTokenFilter;
	
	@Autowired
	private CorsFilter corsFilter;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
		        .exceptionHandling()
				.authenticationEntryPoint(entryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
    }

}