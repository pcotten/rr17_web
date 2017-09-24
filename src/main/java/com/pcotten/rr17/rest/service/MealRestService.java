package com.pcotten.rr17.rest.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.Recipe;

@Controller
public interface MealRestService {

	// covered by UserRestService /users/{userId}/meals/{mealId}
//	@RequestMapping(value="/meals/{mealId}", method=RequestMethod.PUT)
//	public ResponseEntity<Void> updateMeal(
//			@PathVariable ("mealId") Integer mealId,
//			@RequestBody Meal meal);
	
//	@RequestMapping(value="/meals/{mealId}", method=RequestMethod.DELETE)
//	public ResponseEntity<Void> deleteMeal(
//			@PathVariable ("mealId") Integer mealId);
	
	@RequestMapping(value="/meals/{mealId}/recipes", method=RequestMethod.GET)
	public ResponseEntity<List<Recipe>> getMealRecipes(
			@PathVariable ("mealId") Integer mealId);
	
	// covered by recipeRestService /recipe/{recipeId}  -- mealId unnecessary
//	@RequestMapping(value="/meals/{mealId}/recipes/{recipeId}", method=RequestMethod.GET)
//	public ResponseEntity<Recipe> getMealRecipe(
//			@PathVariable ("mealId") Integer mealId,
//			@PathVariable ("recipeId") Integer recipeId);
	
	@RequestMapping(value="/meals/{mealId}/recipes/{recipeId}", method=RequestMethod.POST)
	public ResponseEntity<Void> addRecipeToMeal(
			@PathVariable ("mealId") Integer mealId,
			@PathVariable ("recipeId") Integer recipeId);
	
	@RequestMapping(value="/meals/{mealId}/recipes/{recipeId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> removeRecipeFromMeal(
			@PathVariable ("mealId") Integer mealId,
			@PathVariable ("recipeId") Integer recipeId);
}
