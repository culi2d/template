package com.shop.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.shop.dao.UserDao;
import com.shop.model.Users;

@Repository
public class UserDaoImpl extends AbstractDaoImpl implements UserDao {

	@Override
	public Users findUserById(long id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(Users.class);
		cr.add(Restrictions.eq("userId", id));
		return (Users) cr.uniqueResult();
	}

	@Override
	public Users findUserByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(Users.class);
		cr.add(Restrictions.eq("email", email));
		return (Users) cr.uniqueResult();
	}

}
