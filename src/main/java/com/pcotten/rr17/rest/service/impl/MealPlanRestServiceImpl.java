package com.pcotten.rr17.rest.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.rest.service.MealPlanRestService;
import com.pcotten.rr17.service.MealPlanService;

@RestController
public class MealPlanRestServiceImpl implements MealPlanRestService {

	@Inject
	MealPlanService mealPlanService;
	
	@Override
	public ResponseEntity<Void> updateMealPlan(
			@PathVariable Integer mealPlanId, 
			@RequestBody String mealPlan) {
		
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteMealPlan(
			@PathVariable Integer mealPlanId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<Recipe>> getMealPlanMeals(
			@PathVariable Integer mealPlanId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> createMealPlanMeal(
			@PathVariable Integer mealPlanId, 
			@RequestBody String payload, 
			UriComponentsBuilder uriBuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteMealPlanMeal(
			@PathVariable Integer mealPlanId, 
			@PathVariable Integer recipeId) {
		// TODO Auto-generated method stub
		return null;
	}

}
