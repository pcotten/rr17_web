package com.pcotten.rr17.rest.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.rest.service.MealRestService;

@RestController
public class MealRestServiceImpl implements MealRestService {

	@Override
	public ResponseEntity<Void> updateMeal(
			@PathVariable Integer mealId, 
			@RequestBody String payload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteMeal(
			@PathVariable Integer mealId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<Recipe>> getMealRecipes(
			@PathVariable Integer mealId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> createMealRecipe(
			@PathVariable Integer mealId, 
			@RequestBody String payload, 
			UriComponentsBuilder uriBuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteMealRecipe(
			@PathVariable Integer mealId, 
			@PathVariable Integer recipeId) {
		// TODO Auto-generated method stub
		return null;
	}

}
