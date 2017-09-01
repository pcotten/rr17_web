package com.pcotten.rr17.storage.entity;

import java.sql.SQLException;

import com.pcotten.rr17.model.Cookbook;

public interface CookbookService {

	public Cookbook insertNewCookbook(Cookbook cookbook, Integer userId) throws SQLException;
	
	public int updateCookbook(Cookbook cookbook) throws SQLException;
	
	public int deleteCookbook(Integer id) throws SQLException;

	public Cookbook getCookbookById(Integer id);
	
}
