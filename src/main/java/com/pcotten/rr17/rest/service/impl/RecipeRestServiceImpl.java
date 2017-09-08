package com.pcotten.rr17.rest.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.rest.service.RecipeRestService;

@RestController
public class RecipeRestServiceImpl implements RecipeRestService {

	@Override
	public ResponseEntity<List<Recipe>> getRecipes(
			@RequestParam String category,
			@RequestParam String name,
			@RequestParam String username) {
		return null;
	}
	
	@Override
	public ResponseEntity<Void> updateRecipe(
			@PathVariable Integer recipeId, 
			@RequestBody String payload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteRecipe(
			@PathVariable Integer recipeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<Ingredient>> getRecipeIngredients(
			@PathVariable Integer recipeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> createRecipeIngredient(
			@PathVariable Integer recipeId, 
			@RequestBody String payload,
			UriComponentsBuilder uriBuilder) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ResponseEntity<Void> updateRecipeIngredient(
			@PathVariable Integer recipeId, 
			@PathVariable Integer ingredientId, 
			@RequestBody String payload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteRecipeIngredient(
			@PathVariable Integer recipeId, 
			@PathVariable Integer ingredientId) {
		// TODO Auto-generated method stub
		return null;
	}

}
