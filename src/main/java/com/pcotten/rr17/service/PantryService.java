package com.pcotten.rr17.service;

import java.sql.SQLException;

import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Pantry;

public interface PantryService {

	public Pantry createPantry(Pantry pantry) throws SQLException;
	
	public int updatePantry(Pantry pantry) throws SQLException;
	
	public int deletePantry(Integer id) throws SQLException;

	public Pantry getPantryById(Integer id);

	boolean pantryIngredientExists(Integer userId, Ingredient ingredient);

	public Integer getPantryId(Integer userId);
	
}
