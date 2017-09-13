package com.pcotten.rr17.dao;

import com.pcotten.rr17.model.User;

public interface UserDAO {

	public User createUser(User user);
	
	public User getUser(Integer id);
	
	public Integer updateUser(User user);
	
	public Integer deleteUser(Integer id);
	
}
