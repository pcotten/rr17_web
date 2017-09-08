package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.model.Recipe;

public interface CookbookService {

	public Cookbook createCookbook(Cookbook cookbook, Integer userId) throws SQLException;
	
	public int updateCookbook(Cookbook cookbook) throws SQLException;
	
	public int deleteCookbook(Integer id) throws SQLException;

	public Cookbook getCookbookById(Integer id);

	public List<Recipe> getCookbookRecipes(Integer id);

	public List<Category> getCookbookCategories(Integer id);

	boolean cookbookExists(Integer userId, Cookbook cookbook);
	
}
