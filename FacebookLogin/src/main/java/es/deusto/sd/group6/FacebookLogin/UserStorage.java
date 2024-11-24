package es.deusto.sd.group6.FacebookLogin;

import java.util.HashMap;
import java.util.Map;


public class UserStorage {

	private static UserStorage instance = new UserStorage();
	private Map<String, String> users;

	private UserStorage() {
		users = new HashMap<>();
	}

	public void addUser(String username, String password) {
		users.put(username, password);
	}

	public String getPassword(String username) {
		return users.get(username);
	}

	public static  UserStorage getInstance() {
		return instance;
	}

}
