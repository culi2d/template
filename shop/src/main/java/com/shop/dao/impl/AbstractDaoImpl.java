package com.shop.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class AbstractDaoImpl {
	/** The session factory. */
	@Autowired
	protected SessionFactory sessionFactory;
}
