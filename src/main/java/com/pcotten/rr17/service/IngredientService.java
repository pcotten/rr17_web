package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Ingredient;

public interface IngredientService {

	public Ingredient createIngredient(Ingredient ingredient) throws SQLException;
	
	public int updateIngredient(Ingredient ingredient) throws SQLException;
	
	public int deleteIngredient(Integer id) throws SQLException;

	public Ingredient getIngredientById(Integer id);

	public List<Category> getIngredientCategories(Integer id);
	
	public Ingredient createRecipeIngredient(Ingredient ingredient, Integer recipeId);
	
	public Ingredient createPantryIngredient(Ingredient ingredient, Integer pantryId);

	public Integer getIngredientIdByName(String name);
}
