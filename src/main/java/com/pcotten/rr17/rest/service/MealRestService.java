package com.pcotten.rr17.rest.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcotten.rr17.model.Recipe;

@Controller
public interface MealRestService {

	@RequestMapping(value="/meals/{mealId}", method=RequestMethod.PUT)
	public ResponseEntity<Void> updateMeal(
			@PathVariable ("mealId") Integer mealId,
			@RequestBody String payload);
	
	@RequestMapping(value="/meals/{mealId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteMeal(
			@PathVariable ("mealId") Integer mealId);
	
	@RequestMapping(value="/meals/{mealId}/recipes", method=RequestMethod.GET)
	public ResponseEntity<List<Recipe>> getMealRecipes(
			@PathVariable ("mealId") Integer mealId);
	
	@RequestMapping(value="/meals/{mealId}/recipes", method=RequestMethod.POST)
	public ResponseEntity<Void> createMealRecipe(
			@PathVariable ("mealId") Integer mealId,
			@RequestBody String payload,
			UriComponentsBuilder uriBuilder);
	
	@RequestMapping(value="/meals/{mealId}/recipes/{recipeId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteMealRecipe(
			@PathVariable ("mealId") Integer mealId,
			@PathVariable ("recipeId") Integer recipeId);
}
