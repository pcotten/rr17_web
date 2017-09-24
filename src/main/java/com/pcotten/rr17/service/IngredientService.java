package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Ingredient;

public interface IngredientService {

	public Ingredient getIngredient(Integer id);
	
	public Ingredient createIngredient(Ingredient ingredient) throws SQLException;
	
	public boolean updateIngredient(Ingredient ingredient) throws SQLException;
	
	public boolean deleteIngredient(Integer id) throws SQLException;

	public List<Category> getIngredientCategories(Integer id);
	
	public List<Ingredient> getRecipeIngredients(Integer recipeId);
	
	public boolean addIngredientToRecipe(Ingredient ingredient, Integer recipeId) throws SQLException;
	
	public boolean updateRecipeIngredient(Ingredient ingredient, Integer recipeId);
	
	public boolean removeIngredientFromRecipe(Integer ingredientId, Integer recipeId);
	
	public List<Ingredient> getPantryIngredients(Integer pantryId);
	
	public boolean addIngredientToPantry(Ingredient ingredient, Integer pantryId) throws SQLException;

	public boolean updatePantryIngredient(Ingredient ingredient, Integer pantryId);
	
	public boolean removeIngredientFromPantry(Integer ingredientId, Integer pantryId);	
	
	public Integer getIngredientId(String name);

	public boolean ingredientExists(Ingredient ingredient) throws SQLException;




}
