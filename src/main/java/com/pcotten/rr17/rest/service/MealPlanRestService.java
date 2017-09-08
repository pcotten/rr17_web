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
public interface MealPlanRestService {
	
	@RequestMapping(value="/mealplans/{mealPlanId}", method=RequestMethod.PUT)
	public ResponseEntity<Void> updateMealPlan(
			@PathVariable ("mealPlanId") Integer mealId,
			@RequestBody String payload);
	
	@RequestMapping(value="/mealplans/{mealPlanId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteMealPlan(
			@PathVariable ("mealPlanId") Integer mealId);
	
	@RequestMapping(value="/mealplans/{mealPlanId}/meals", method=RequestMethod.GET)
	public ResponseEntity<List<Recipe>> getMealPlanMeals(
			@PathVariable ("mealPlanId") Integer mealId);
	
	@RequestMapping(value="/mealplans/{mealPlanId}/meals", method=RequestMethod.POST)
	public ResponseEntity<Void> createMealPlanMeal(
			@PathVariable ("mealPlanId") Integer mealId,
			@RequestBody String payload,
			UriComponentsBuilder uriBuilder);
	
	@RequestMapping(value="/mealplans/{mealPlanId}/meals/{mealId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteMealPlanMeal(
			@PathVariable ("mealPlanId") Integer mealId,
			@PathVariable ("mealId") Integer recipeId);

}
