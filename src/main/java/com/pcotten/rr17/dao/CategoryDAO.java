package com.pcotten.rr17.dao;

import java.util.List;

import com.pcotten.rr17.model.Category;

public interface CategoryDAO {

	public Category getCategory(Integer id);
	
	public Category createCategory(Category category);
	
	public Integer updateCategory(Category category);
	
	public Integer deleteCategory(Integer id);
	
	public List<Category> getRecipeCategories(Integer recipeId);
	
	public List<Category> getMealCategories(Integer mealId);
	
	public List<Category> getIngredientCategories(Integer ingredientId);
	
	public List<Category> getCookbookCategories(Integer cookbookId);
}
