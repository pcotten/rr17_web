package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Ingredient;

public interface IngredientService {

	public Ingredient getIngredient(Integer id);
	
	public Ingredient createIngredient(Ingredient ingredient) throws SQLException;
	
	public Integer updateIngredient(Ingredient ingredient) throws SQLException;
	
	public Integer deleteIngredient(Integer id) throws SQLException;

	public List<Category> getIngredientCategories(Integer id);
	
	public List<Ingredient> getRecipeIngredients(Integer recipeId);
	
	public Integer createRecipeIngredient(Ingredient ingredient, Integer recipeId) throws SQLException;
	
	public Integer updateRecipeIngredient(Ingredient ingredient, Integer recipeId);
	
	public Integer deleteRecipeIngredient(Integer ingredientId, Integer recipeId);
	
	public List<Ingredient> getPantryIngredients(Integer pantryId);
	
	public Integer createPantryIngredient(Ingredient ingredient, Integer pantryId) throws SQLException;

	public Integer updatePantryIngredient(Ingredient ingredient, Integer pantryId);
	
	public Integer deletePantryIngredient(Integer ingredientId, Integer pantryId);	
	
	public Integer getIngredientId(String name);

	public boolean ingredientExists(Ingredient ingredient) throws SQLException;




}
