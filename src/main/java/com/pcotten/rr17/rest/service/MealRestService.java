package com.pcotten.rr17.rest.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pcotten.rr17.model.Cookbook;

@Controller
public interface MealRestService {

	@RequestMapping(value="/meals/{mealId}", method=RequestMethod.PUT)
	public Cookbook updateMeal(
			@PathVariable ("mealId") Integer mealId,
			@RequestBody String payload);
	
	@RequestMapping(value="/meals/{mealId}", method=RequestMethod.DELETE)
	public boolean deleteMeal(
			@PathVariable ("mealId") Integer mealId);
	
	@RequestMapping(value="/meals/{mealId}/recipes", method=RequestMethod.GET)
	public Cookbook getMealRecipes(
			@PathVariable ("mealId") Integer mealId);
	
	@RequestMapping(value="/meals/{mealId}/recipes", method=RequestMethod.POST)
	public boolean createMealRecipe(
			@PathVariable ("mealId") Integer mealId,
			@RequestBody String payload);
	
	@RequestMapping(value="/meals/{mealId}/recipes/{recipeId}", method=RequestMethod.DELETE)
	public boolean deleteMealRecipe(
			@PathVariable ("mealId") Integer mealId,
			@PathVariable ("recipeId") Integer recipeId);
}
