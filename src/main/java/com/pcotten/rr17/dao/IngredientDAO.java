package com.pcotten.rr17.dao;

import java.util.List;
import java.util.Map;

import com.pcotten.rr17.model.Ingredient;

public interface IngredientDAO {

	public Ingredient getIngredient(Integer id);
	
	public Ingredient createIngredient(Ingredient ingredient);
	
	public Integer updateIngredient(Ingredient ingredient);
	
	public Integer deleteIngredient(Integer id);
	
	public List<Ingredient> getPantryIngredients(Integer pantryId);
	
	public Integer createPantryIngredient(Ingredient ingredient, Integer pantryId);

	public Integer updatePantryIngredient(Integer pantryId, Ingredient ingredient);

	public Integer deletePantryIngredient(Integer pantryId, Integer ingredientId);

	public Map<Integer, List<Ingredient>> getRecipeIngredients(Integer recipeId);
	
	public Integer createRecipeIngredient(Ingredient ingredient, Integer recipeId);

	public Integer updateRecipeIngredient(Ingredient ingredient, Integer recipeId);

	public Integer removeIngredientFromRecipe(Integer ingredientId, Integer recipeId);
	
	public Ingredient getIngredientByName(String name);
}
