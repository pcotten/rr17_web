package com.pcotten.rr17.storage.entity;

import java.sql.SQLException;

import com.pcotten.rr17.model.User;

public interface UserService {

	public User insertNewUser(User user) throws SQLException;
	
	public int updateUser(User user) throws SQLException;
	
	public int deleteUser(Integer id) throws SQLException;

	public User getUserByUsername(String username);
	
}
