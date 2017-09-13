package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Pantry;

public interface PantryService {

	public Pantry getPantryById(Integer id);
	
	public Pantry createPantry(Pantry pantry) throws SQLException;
	
	public boolean updatePantry(Pantry pantry) throws SQLException;
	
	public boolean deletePantry(Integer id) throws SQLException;
	
	public List<Ingredient> getPantryIngredients(Integer userId);
	
	public boolean createPantryIngredient(Ingredient ingredient, Integer userId);

	public boolean updatePantryIngredient(Integer userId, Ingredient ingredient);

	public boolean deletePantryIngredient(Integer userId, Integer ingredientId);

	public boolean pantryIngredientExists(Integer userId, Ingredient ingredient);
	
	public Integer getPantryId(Integer userId);



	
}
