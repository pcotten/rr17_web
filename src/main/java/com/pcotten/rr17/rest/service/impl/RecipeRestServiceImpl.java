package com.pcotten.rr17.rest.service.impl;

import java.util.List;

import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.rest.service.RecipeRestService;

public class RecipeRestServiceImpl implements RecipeRestService {

	@Override
	public Recipe updateRecipe(Integer recipeId, String payload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteRecipe(Integer recipeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Ingredient> getRecipeIngredients(Integer recipeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ingredient updateRecipeIngredient(Integer recipeId, Integer ingredientId, String payload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ingredient deleteRecipeIngredient(Integer recipeId, Integer ingredientId) {
		// TODO Auto-generated method stub
		return null;
	}

}
