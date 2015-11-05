package com.icoding.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.icoding.domain.User;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Integer> implements
		UserDao {

	@SuppressWarnings("unchecked")
	@Override
	public User getUser(String username) {
		List<User> userList = new ArrayList<User>();
		Query query = currentSession().createQuery(
				"from User u where u.username = :username");
		query.setParameter("username", username);
		userList = query.list();
		if (userList.size() > 0)
			return userList.get(0);
		else
			return null;
	}

	@Override
	public User getUser(String username, String password) {
		List<User> userList = new ArrayList<User>();
		Query query = currentSession()
				.createSQLQuery(
						"from User u where u.username = :username and u.password = :password");
		query.setParameter("username", username);
		query.setParameter("password", password);
		userList = query.list();
		if (userList.size() > 0)
			return userList.get(0);
		else
			return null;
	}

	@Override
	public List<User> getListUserEE() {
		List<User> listAll = getAll();
		List<User> userList = new ArrayList<User>();
		for (User u : listAll) {
			if(u.getRole().getName().equalsIgnoreCase("ee")){
				userList.add(u);
			}
		}
		return userList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getListUserPL() {
		List<User> listAll = getAll();
		List<User> userList = new ArrayList<User>();
		for (User u : listAll) {
			if(u.getRole().getName().equalsIgnoreCase("pl")){
				userList.add(u);
			}
		}
		return userList;
	}

}
