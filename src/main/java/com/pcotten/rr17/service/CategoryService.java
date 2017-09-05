package com.pcotten.rr17.service;

import java.sql.SQLException;

import com.pcotten.rr17.model.Category;

public interface CategoryService {

	public Category insertNewCategory(Category category) throws SQLException;
	
	public int updateCategory(Category category) throws SQLException;
	
	public int deleteCategory(Integer id) throws SQLException;

	public Category getCategoryByName(String name);
	
}
