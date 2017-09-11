package com.pcotten.rr17.rest.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.rest.service.RecipeRestService;
import com.pcotten.rr17.service.RecipeService;

@RestController
public class RecipeRestServiceImpl implements RecipeRestService {
	
	@Inject
	RecipeService service;

	@Override
	public ResponseEntity<List<Recipe>> getRecipes(
			@RequestParam String category,
			@RequestParam String title,
			@RequestParam String username) {

		ResponseEntity<List<Recipe>> response = null;
		List<Recipe> recipes = new ArrayList<Recipe>();
		
		try {
			recipes = service.getRecipes(category, title, username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (recipes != null && !recipes.isEmpty()) {
			response = ResponseEntity.ok(recipes);
		}
		else {
			response = new ResponseEntity<List<Recipe>>(HttpStatus.NOT_FOUND);
		}
		
		return response;
	}
	
	@Override
	public ResponseEntity<Void> updateRecipe(
			@PathVariable Integer recipeId, 
			@RequestBody Recipe recipe) {

		ResponseEntity<Void> response = null;
		
		try {
			boolean success = service.updateRecipe(recipe);
			if (success) {
				response = ResponseEntity.ok().build();
			}
			else {
				response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ResponseEntity<Void> deleteRecipe(
			@PathVariable Integer recipeId) {
		
		ResponseEntity<Void> response = null;
		
		try {
			boolean success = service.deleteRecipe(recipeId);
			if (success) {
				response = ResponseEntity.ok().build();
			}
			else {
				response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ResponseEntity<List<Ingredient>> getRecipeIngredients(
			@PathVariable Integer recipeId) {

		ResponseEntity<List<Ingredient>> response = null;
		
		return response;
	}

	@Override
	public ResponseEntity<Void> createRecipeIngredient(
			@PathVariable Integer recipeId, 
			@RequestBody String payload,
			UriComponentsBuilder uriBuilder) {
		
		ResponseEntity<Void> response = null;
		
		return response;
	}
	
	@Override
	public ResponseEntity<Void> updateRecipeIngredient(
			@PathVariable Integer recipeId, 
			@PathVariable Integer ingredientId, 
			@RequestBody Ingredient ingredient) {
		
		ResponseEntity<Void> response = null;
		
		return response;
	}

	@Override
	public ResponseEntity<Void> deleteRecipeIngredient(
			@PathVariable Integer recipeId, 
			@PathVariable Integer ingredientId) {
		
		ResponseEntity<Void> response = null;
		
		return response;
	}

}
