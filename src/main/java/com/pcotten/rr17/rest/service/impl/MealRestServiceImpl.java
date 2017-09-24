package com.pcotten.rr17.rest.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.rest.service.MealRestService;
import com.pcotten.rr17.service.MealService;
import com.pcotten.rr17.service.RecipeService;

@RestController
public class MealRestServiceImpl implements MealRestService {
	
	@Inject
	MealService service;
	@Inject
	RecipeService recipeService;

//	@Override
//	public ResponseEntity<Void> updateMeal(
//			@PathVariable Integer mealId, 
//			@RequestBody Meal meal) {
//
//		ResponseEntity<Void> response = null;
//		
//		boolean success;
//		try {
//			success = service.updateMeal(meal);
//			if (success) {
//				response = ResponseEntity.ok().build();
//			}
//			else {
//				response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
//			}
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
//		
//		return response;
//	}

//	@Override
//	public ResponseEntity<Void> deleteMeal(
//			@PathVariable Integer mealId) {
//
//		ResponseEntity<Void> response = null;
//		
//		boolean success = service.deleteMeal(id, userId)
//		
//		return response;
//	}

	@Override
	public ResponseEntity<List<Recipe>> getMealRecipes(
			@PathVariable Integer mealId) {
		
		ResponseEntity<List<Recipe>> response = null;
		
		List<Recipe> recipes = recipeService.getMealRecipes(mealId);
		if (recipes != null && !recipes.isEmpty()) {
			response = ResponseEntity.ok(recipes);
		}
		else {
			response = new ResponseEntity<List<Recipe>>(HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
//	@Override
//	public ResponseEntity<Recipe> getMealRecipe(
//			@PathVariable Integer mealId, 
//			@PathVariable Integer recipeId) {
//
//		ResponseEntity<Recipe> response = null;
//		
//		Recipe recipe = recipeService.getRecipe(recipeId);
//		if (recipe != null) {
//			response = ResponseEntity.ok(recipe);
//		}
//		else {
//			response = new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
//		}
//		return response;
//	}

	@Override
	public ResponseEntity<Void> addRecipeToMeal(
			@PathVariable Integer mealId, 
			@PathVariable Integer recipeId) {

		ResponseEntity<Void> response = null;
		
		boolean success = service.addRecipeToMeal(mealId, recipeId);
		if (success) {
			response = ResponseEntity.ok().build();
		}
		else {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		
		return response;
	}

	@Override
	public ResponseEntity<Void> removeRecipeFromMeal(
			@PathVariable Integer mealId, 
			@PathVariable Integer recipeId) {

		ResponseEntity<Void> response = null;
		
		boolean success = service.removeRecipeFromMeal(mealId, recipeId);
		if (success) {
			response = ResponseEntity.ok().build();
		}
		else {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		return response;
	}



}
