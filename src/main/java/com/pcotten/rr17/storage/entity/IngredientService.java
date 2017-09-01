package com.pcotten.rr17.storage.entity;

import java.sql.SQLException;

import com.pcotten.rr17.model.Ingredient;

public interface IngredientService {

	public Ingredient insertNewIngredient(Ingredient ingredient) throws SQLException;
	
	public int updateIngredient(Ingredient ingredient) throws SQLException;
	
	public int deleteIngredient(Integer id) throws SQLException;

	public Ingredient getIngredientById(Integer id);
	
}
