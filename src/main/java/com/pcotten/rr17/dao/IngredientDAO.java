package com.pcotten.rr17.dao;

import java.util.List;

import com.pcotten.rr17.model.Ingredient;

public interface IngredientDAO {

	public Ingredient getIngredient(Integer id);
	
	public Ingredient createIngredient(Ingredient ingredient);
	
	public Integer updateIngredient(Ingredient ingredient);
	
	public Integer deleteIngredient(Integer id);
	
	public List<Ingredient> getPantryIngredients(Integer pantryId);
	
	public List<Ingredient> getRecipeIngredients(Integer recipeId);
	
}
