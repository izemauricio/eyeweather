package com.mauricio.eyeweather.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserDao user_store;

	public void load() {
		user_store.load();
	}

	public User getUserById(String id) {
		for (User user : user_store.getList()) {
			if (user.getId().equals(id))
				return user;
		}
		return null;
	}

	public User getUser(String username, String password) {
		if (username == null || password == null)
			return null;
		for (User user : user_store.getList()) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password))
				return user;
		}
		return null;
	}
}
