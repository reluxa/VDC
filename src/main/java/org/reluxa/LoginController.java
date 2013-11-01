package org.reluxa;

import javax.inject.Inject;

import org.reluxa.db.Session;
import org.reluxa.model.User;

import com.db4o.ObjectSet;

public class LoginController {

	@Inject
	Session db;
	
	public void createUser(User user) {
		db.store(user);
	}
	
	public boolean queryUser(String username, String password) {
		User user = createUserTemplate(username, password);
		ObjectSet<User> users = db.queryByExample(user);
		return users.size() > 0;
	}
	
	private User createUserTemplate(String userName, String password) {
		User user = new User();
		user.setUsername(userName);
		user.setPassword(password);
		return user;
	}
	
}
